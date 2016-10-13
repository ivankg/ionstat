package org.strmln.ionstat.mvc.controllers;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.strmln.ionstat.model.Policy;
import org.strmln.ionstat.model.UserProfile;
import org.strmln.ionstat.model.context.UserProfileContext;
import org.strmln.ionstat.mvc.commands.TaskCommand;
import org.strmln.ionstat.mvc.model.ModelMapResponse;
import org.strmln.ionstat.mvc.utils.DownloadUtils;
import org.strmln.ionstat.mvc.utils.JsonUtils;
import org.strmln.ionstat.task.handler.AbstractTaskHandler;
import org.strmln.ionstat.task.handler.TaskHandler;
import org.strmln.ionstat.task.handler.manager.TaskManager;
import org.strmln.ionstat.task.handler.model.Task;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;

@Controller
@RequestMapping(value = "task.is")
public class TaskController extends AbstractController {

	private static final String TASK_RESPONSE_KEY = "taskResponse";
	private static final String AVAILABLE_TASKS_KEY = "availableTasks";
	private static final String JAVA_TEMP_DIR = "java.io.tmpdir";
	
	@Autowired
	private TaskManager _taskManager;

	public TaskManager getTaskManager() {
		return _taskManager;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public ModelMapResponse handleGet(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TaskCommand taskCommand) {

		TaskHandler taskHandler = getTaskHandler(taskCommand);

		Object taskHandlerCommand = getTaskHandlerCommand(taskCommand, taskHandler);

		TaskResponse taskResponse = taskHandler.getTaskInfos(taskHandlerCommand);

		return handleTaskResponse(taskResponse, response);

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ModelMapResponse handlePost(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute TaskCommand taskCommand) {

		List<Path> uploadedFiles = new ArrayList<>();

		Path tmpFolder = Paths.get(System.getProperty(JAVA_TEMP_DIR), UUID.randomUUID().toString());
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multiparRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> files = multiparRequest.getFileMap();
			for (Entry<String, MultipartFile> file : files.entrySet()) {
				Path tmpFile = tmpFolder.resolve(file.getValue().getOriginalFilename());
				try (InputStream inputStream = file.getValue().getInputStream();
						OutputStream outputStream = Files.newOutputStream(tmpFile)) {
					IOUtils.copy(inputStream, outputStream);
					
					uploadedFiles.add(tmpFile);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		TaskHandler taskHandler = getTaskHandler(taskCommand);
		Object taskHandlerCommand = getTaskHandlerCommand(taskCommand, taskHandler);

		TaskResponse taskResponse = taskHandler.processTask(taskHandlerCommand, uploadedFiles);

		return handleTaskResponse(taskResponse, response);
	}

	private void addAvailableTasksToResponse(ModelMapResponse modelMapResponse, Task[] supportedTasks) {
		List<String> availableTasks = new ArrayList<>();
		Map<String, AbstractTaskHandler> allTasks = getTaskManager().getTaskHandlerMap();
		UserProfile currentUserProfile = UserProfileContext.getCurrentUserProfile();
		Set<Policy> policies = currentUserProfile.getRole().getPolicies();
		for (Task task : supportedTasks) {
			AbstractTaskHandler taskHandler = allTasks.get(task.getTaskName());
			for (Policy policy : policies) {
				if (policy.getPolicyId().equals(taskHandler.getPolicy())) {
					availableTasks.add(task.getTaskName());
				}
			}
		}
		modelMapResponse.put(AVAILABLE_TASKS_KEY, availableTasks);
	}

	private TaskHandler getTaskHandler(TaskCommand taskCommand) {
		String taskName = taskCommand.getTaskName();

		TaskHandler taskHandler = getTaskManager().getTaskHandler(taskName);
		return taskHandler;
	}

	private Object getTaskHandlerCommand(TaskCommand taskCommand, TaskHandler taskHandler) {
		Class<? extends DtoTaskHandlerCommand<?>> taskHandlerCommandClass = taskHandler.getCommandClass();
		DtoTaskHandlerCommand<?> dtoTaskHandlerCommand = JsonUtils.readValue(taskCommand.getJsonTaskData(),
				taskHandlerCommandClass);
		return dtoTaskHandlerCommand.convertToInternalTaskHandlerCommand();
	}

	private ModelMapResponse handleTaskResponse(TaskResponse taskResponse, HttpServletResponse response) {
		if (taskResponse.getInputStream() != null) {
			DownloadUtils.fillDownloadResponse(taskResponse.getResourceName(), taskResponse.getInputStream(), response);
			return null;
		}

		ModelMapResponse modelMapResponse = new ModelMapResponse();
		modelMapResponse.put(TASK_RESPONSE_KEY, taskResponse.getResponseModel());
		if (taskResponse.getSupportedTasks() != null) {
			addAvailableTasksToResponse(modelMapResponse, taskResponse.getSupportedTasks());
		}

		return modelMapResponse;
	}

}
