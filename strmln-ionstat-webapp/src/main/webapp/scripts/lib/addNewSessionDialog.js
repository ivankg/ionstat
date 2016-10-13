$(function() {
	
})

function addNewSessionBtnHandler(){
	var jsonData = {};
	jsonData.taskName = 'createSession';
	var jsonTaskData = {};
	jsonTaskData.deviceId = $("#tree").dynatree("getTree").activeNode.data.id;
	$.ajax({
		type : "GET",
		url : 'task.is',
		data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
		dataType : "json",
		success : function(data) {
			createNewSessionDialogItems(data)
		}
	})
}

function createNewSessionDialogItems(ajaxResponse) {
	$.sessionTemplates = ajaxResponse.taskResponse.sessionTemplates;
	if(!this.firstTime){
		this.firstTime = true;
	
		$('#addNewSessionDialogUi').window({
		    width:700,
		    height:450,
		    resizable: false,
		    modal:true,
		    minimizable: false,
		    maximizable: false,
		    collapsible: false,
		    title: 'Add new session'
		});

		$('#addNewSessionDialogUi').layout();
		$('#addNewSessionDialogUi').layout('add',{
		    region: 'north',
		    height: 383,
		    tools: []
		});
		$('#addNewSessionDialogUi').layout('add',{
		    region: 'south',
		    height: 30,
		    tools: []
		});
		var southPanel = $('#addNewSessionDialogUi').layout('panel','south')
		southPanel.append(
			$('<a></a>')
				.attr('id', 'saveBtnAddSession')
				.attr('href', '#')
				.css('margin-left', '480px')
				.css('margin-top', '3px')
		)
		southPanel.append(
			$('<a></a>')
				.attr('id', 'cancelBtnAddSession')
				.attr('href', '#')
				.css('margin-left', '10px')
				.css('margin-top', '3px')
		)
		$('#saveBtnAddSession').linkbutton({
		    iconCls: 'icon-save',
		    text: 'Save'
		});
		$('#cancelBtnAddSession').linkbutton({
		    iconCls: 'icon-cancel',
		    text: 'Cancel'
		});
		$('#saveBtnAddSession').bind('click', function(){
	        addSessionDialogOnSave();
	    });
		$('#cancelBtnAddSession').bind('click', function(){
			$('#addNewSessionDialogUi').window('close')
	    });
		var northPanel = $('#addNewSessionDialogUi').layout('panel','north')
		northPanel.append(
				$('<input>')
				.attr('id', 'UploadFileChooseFile')
				.attr('type', 'file')
				.css('margin-top', '10px')
		)
		.append(
			$('</br>')
		)
		.append(
			$('<select></select>')
				.attr('id', 'comboBox')
				.css('width', '300px')
				.css('margin-left', '20px')
		)
		.append(
			$('<input>')
				.attr('id', 'UploadFileSubmit')
				.attr('type', 'submit')
				.css('width', '50px')
				.css('height', '30px')
		)
		.append(
			$('</br>')
		).append(
			$('<table></table>')
				.attr('id', 'nominalGridUi')
		)
		.append(
				$('</br>')
			)
			northPanel.append(
				$('<div></div>')
					.attr('id', 'inputGridUiContainer').append(
							$('<table></table>')
							.attr('id', 'inputGridUi')
					)
					.append(
							$('</br>')
						)
			)
		
	}
	if($('#inputGridUiContainer')){
		$('#inputGridUiContainer').hide();
	}
	if($('#recordedGridUiContainer')){
		$('#recordedGridUiContainer').hide();
	}
	$.recordedData = null;
	$("#UploadFileSubmit").attr('disabled', false);
	$("#UploadFileChooseFile").attr('disabled', false);
	$("#comboBox").attr('disabled', false);
	if(!$.sessionTemplates[0].uploadRequired){
		$("#UploadFileSubmit").hide();
		$("#UploadFileChooseFile").hide();
		createInputGridUi($.sessionTemplates[0])
	}else{
		$("#UploadFileSubmit").show();
		$("#UploadFileChooseFile").show();
	}
	addComboBoxData($.sessionTemplates);
	createNominalGridUi($.sessionTemplates[0]);
	$.selectedComboData = $.sessionTemplates[0]
	$('#addNewSessionDialogUi').window('open');
	var select = $('#comboBox');
    select.bind("click", function (e) {
    	var selectedRecordId = e.target.value;
    	$.selectedComboData = getSelectedRecordData(selectedRecordId, $.sessionTemplates)
    	createNominalGridUi($.selectedComboData)
    	if(!$.selectedComboData.uploadRequired){
    		createInputGridUi($.selectedComboData)
    		$("#UploadFileSubmit").hide();
    		$("#UploadFileChooseFile").hide();
    	}else{	
    		$('#inputGridUiContainer').hide();
    		$("#UploadFileSubmit").show();
    		$("#UploadFileChooseFile").show();
    	}
    });

    $("#UploadFileSubmit").click(function(button) {
		var uploadedFile = $('#UploadFileChooseFile')[0].files[0];
		if(uploadedFile){
			var data = new FormData();
			var sessionTemplateId = $('#comboBox')[0].value;
			var jsonData = {};
			jsonData.taskName = 'uploadSession';
			var jsonTaskData = {};
			jsonTaskData.sessionTemplateId = sessionTemplateId;
			data.append('jsonData', JSON.stringify(jsonData))
			data.append('jsonTaskData', JSON.stringify(jsonTaskData))
			data.append('fileName', uploadedFile);
			$.ajax({
				type : "POST",
				url : 'task.is',
				data : data,
				cache : false,
				processData : false,
				contentType : false,
				// dataType: "json",
				crossDomain : true,
				success : function(data, textStatus, jqXHR) {
					$.recordedData = data;
					createRecordedGrid(data);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					//alert('Error: ' + textStatus + ' ' + errorThrown.message);
				}
			});
	    	$("#UploadFileSubmit").attr('disabled', true);
	    	$("#UploadFileChooseFile").attr('value', '');
	    	$("#UploadFileChooseFile").attr('disabled', true);
	    	$("#comboBox").attr('disabled', true);
		}
	})
}
function getSelectedRecordData(selectedRecordId, sessionTemplates){
	var item = 0;
	var recordIndex = -1;
	while(sessionTemplates[item] && recordIndex < 0){
		if(selectedRecordId == sessionTemplates[item].sessionTemplateId){
			recordIndex = item;
		}
		item++;
	}
	return sessionTemplates[recordIndex]
}
function addComboBoxData(sessionTemplates){
	var combo = $('#comboBox');
	if(combo[0].options.length){
		var item = combo[0].options.length - 1;
		var currentOption;
		while(currentOption = combo[0].options[item]){
			currentOption.remove(0);
			item--;
		}
	}
	var item = 0;
	while(sessionTemplates[item]){
		$('#comboBox')
			.append('<option value=' + sessionTemplates[item].sessionTemplateId + '>' + sessionTemplates[item].name + '</option>')
		item++;
	}
}
function addSessionDialogOnSave(sessionTemplates){
	var recorded = false;
	if($.recordedData){
		recorded = true;
	}
	var jsonTaskData = createJsonTaskDataForPost(recorded);
	var jsonData = {};
	jsonData.taskName = 'createSession';
	$.ajax({
		type : "POST",
		url : 'task.is',
		data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
		dataType : "json",
		success : function(data) {
			var selectedDeviceId = $("#tree").dynatree("getTree").activeNode.data.id;
			var jsonData = {};
			jsonData.taskName = "getDevice";
			var jsonTaskData = {};
			jsonTaskData.id = selectedDeviceId;
			$.ajax({
				type : "GET",
				url : 'task.is',
				data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
				dataType : "json",
				success : function(data) {
					updateNewMainGrid(data);
					$('#addNewSessionDialogUi').window('close')
				}
			})
		}
	})
}
function createJsonTaskDataForPost(recorded){
	var jsonTaskData = {};
	var sessionTemplateId = $('#comboBox')[0].value;
	var deviceId = $("#tree").dynatree("getTree").activeNode.data.id;
	var measurementData;
	if(!$.selectedComboData.uploadRequired){
		measurementData = createMeasurementDataFromInput();
	}
	if(recorded){
		measurementData = measurementDataForSave($.recordedData.taskResponse.measurementInfo);
	}
	jsonTaskData.measurementData = measurementData;
	jsonTaskData.deviceId = deviceId;
	jsonTaskData.sessionTemplateId = sessionTemplateId.toString();
	return jsonTaskData;
}
function createMeasurementDataFromInput(){
	var item = 0;
	var measurementData = [];
	var currentHeaderInfo;
	while(currentHeaderInfo = $.selectedComboData.inputMeasurementInfo.headerInfos[item]){
		var currentObject = {}
		currentObject.measureId = currentHeaderInfo.measureId;
		var values = findDateByMeasureName(currentHeaderInfo.measureName);
		currentObject.values = values
		measurementData.push(currentObject)
		item++;
	}
	return measurementData;
}
function findDateByMeasureName(measureName){
	var inputGridUiData = $('#inputGridUi').datagrid('getData');
	var item = 0;
	var currentRow;
	var values = [];
	while(currentRow = inputGridUiData.rows[item]){
		values.push(currentRow[measureName])
		item++;
	}
	return values;
}
function measurementDataForSave(measurementData){
	var item = 0;
	var measurementArray = [];
	var currentHeaderInfos;
	while(currentHeaderInfos = measurementData.headerInfos[item]){
		var item1 = 0;
		var values = [];
		var currentMeasurements;
		while(currentMeasurements = measurementData.measurements[item1]){
			values.push(currentMeasurements[currentHeaderInfos.measureName])
			item1++;
		}
		measurementArray.push({
			values: values,
			measureId: currentHeaderInfos.measureId.toString()
		})
		item++;
	}
	return measurementArray;
}
function createNominalGridUi(sessionTemplates){
	var lastIndex;
	var nominalGridUiObject = createGridColumnsUi(sessionTemplates.nominalMeasurementInfo.headerInfos, 80);
	$('#nominalGridUi').datagrid({
		title: 'Nominal values',
		width: nominalGridUiObject.gridWidth,
		remoteSort : false,
		singleSelect : true,
		border: true,
		nowrap : false,
		cls: 'nominalGridUi',
		bodyCls: 'nominalGridUi-body',
        scrollbarSize:0,
		fitColumns : true,
	    columns:[nominalGridUiObject.gridColumns]
	});
	$('#nominalGridUi').datagrid('loadData', sessionTemplates.nominalMeasurementInfo.measurements)
	
}
function createInputGridUi(sessionTemplates){
	var editIndex = undefined;

	var inputGridUiObject = createGridColumnsUi(sessionTemplates.inputMeasurementInfo.headerInfos, 80);
	$('#inputGridUi').datagrid({
		title: 'Input values',
		width: inputGridUiObject.gridWidth,
		remoteSort : false,
		singleSelect : true,
		border: true,
		nowrap : false,
		cls: 'nominalGridUi',
		bodyCls: 'nominalGridUi-body',
        scrollbarSize:0,
		fitColumns : true,
		onClickCell: function(cellIndex){
    		endEditing(editIndex)
		},
		onDblClickCell:function(cellIndex, fieldName){
    		if(editIndex != cellIndex){
	    		endEditing(editIndex)
	    		}
	    	 if (endEditing()){
	    	        $('#inputGridUi').datagrid('selectRow', cellIndex)
	    	                .datagrid('editCell', {index:cellIndex,field:fieldName});
	    	        editIndex = cellIndex;
	    	    }

	    },
	    onAfterEdit: function(){
	    },
	    columns:[inputGridUiObject.gridColumns],
	});
	$('#inputGridUi').datagrid('loadData', sessionTemplates.inputMeasurementInfo.measurements);
	$('#inputGridUiContainer').show();
}
function endEditing(editIndex){
    if (editIndex == undefined){
    	return true
    }
    if ($('#inputGridUi').datagrid('validateRow', editIndex)){
        $('#inputGridUi').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function createGridColumnsUi(headerInfos, columnWidth) {
	var gridColumns = [];
	var item = 0;
	var headerInfo;
	var gridWidth = 0;
	while (headerInfo = headerInfos[item]) {
		var currentHeaderTextWidth = headerInfo.measureName.length * 8;
		currentHeaderTextWidthToSet = currentHeaderTextWidth > columnWidth ? currentHeaderTextWidth : columnWidth
		var widthToGrid = currentHeaderTextWidth > columnWidth ? currentHeaderTextWidth : columnWidth
		var currentHeader = {
			width: widthToGrid,
			field : headerInfo.measureName,
			editor:'text',
			title : headerInfo.measureName + '<br/>' + ' (' + headerInfo.measureUnit + ')',
			align : 'center',
			sortable : false
		}
		gridWidth = gridWidth + widthToGrid;
		gridColumns.push(currentHeader);
		item++;
	}
	return gridObject = {
			gridColumns:gridColumns,
			gridWidth: gridWidth + 20
		}
}

function createRecordedGridColumnsUi(headerInfos, columnWidth) {
	var gridColumns = [];
	var item = 0;
	var headerInfo;
	var gridWidth = 0;
	while (headerInfo = headerInfos[item]) {
		var currentHeaderTextWidth = headerInfo.measureName.length * 8;
		currentHeaderTextWidthToSet = currentHeaderTextWidth > columnWidth ? currentHeaderTextWidth : columnWidth
		var widthToGrid = currentHeaderTextWidth > columnWidth ? currentHeaderTextWidth : columnWidth
		var currentHeader = {
			width: widthToGrid,
			formatter: function(value,row,index){
				if (value && $.isNumeric(value)){
					return value.toFixed(5);
				}else{
					return value;
				}
			},
			field : headerInfo.measureName,
			editor:'text',
			title : headerInfo.measureName + '<br/>' + ' (' + headerInfo.measureUnit + ')',
			align : 'center',
			sortable : false
		}
		gridWidth = gridWidth + widthToGrid;
		gridColumns.push(currentHeader);
		item++;
	}
	return gridObject = {
			gridColumns:gridColumns,
			gridWidth: gridWidth + 20
		}
}
function createRecordedGrid(data){
	var northPanel = $('#addNewSessionDialogUi').layout('panel','north')
	northPanel.append(
		$('<div></div>')
			.attr('id', 'recordedGridUiContainer')
			.append(
					$('<table></table>')
					.attr('id', 'recordedGridUi')
			)
			.append(
					$('</br>')
				)
	)
	createRecordedGridUi(data)
}
function createRecordedGridUi(data){
	var lastIndex;
	var recordedGridUiObject = createRecordedGridColumnsUi(data.taskResponse.measurementInfo.headerInfos, 80);
	$('#recordedGridUi').datagrid({
		title: 'Recorded values',
		width: recordedGridUiObject.gridWidth,
		remoteSort : false,
		singleSelect : true,
		border: true,
		nowrap : false,
		cls: 'nominalGridUi',
		bodyCls: 'nominalGridUi-body',
        scrollbarSize:0,
		fitColumns : true,
	    columns:[recordedGridUiObject.gridColumns]
	});
	$('#recordedGridUi').datagrid('loadData', data.taskResponse.measurementInfo.measurements);
	$('#recordedGridUiContainer').show();
}

	
