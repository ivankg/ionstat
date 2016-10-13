$(function() {
	$("#cancelBtn_addNewSession").click(function(button) {
		$('#addNewSessionDialog').hide();
		$('#mask').fadeOut();
	});
	/*$("#closeBtn_addNewSession").click(function(button) {
		$('#addNewSessionDialog').hide();
		$('#mask').fadeOut();
	});*/
	$("#confirmButton_addNewSession").click(function(button) {
		saveSession()
		$('#addNewSessionDialog').hide();
		$('#mask').fadeOut();
	});
})
function addNewSessionMethod(){
	addNewSessionDialogMethod();
	var selectedDeviceId = $("#tree").dynatree("getTree").activeNode.data.id;
	$('#addNewSessionDialog').attr('selectedDeviceId', selectedDeviceId);
}
function saveSession(){
	var jsonTaskData = createJsonTaskData();
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
				}
			})
		}
	})
}

function createJsonTaskData(){
	var jsonTaskData = {};
	var sessionTemplateId = $('#comboBox')[0].value;
	var deviceId = $('#addNewSessionDialog')[0].attributes.selecteddeviceid.value;
	var measurementData = measurementDataForSave($.recordedData.taskResponse.measurementInfo)
	jsonTaskData.measurementData = measurementData;
	jsonTaskData.deviceId = deviceId;
	jsonTaskData.sessionTemplateId = sessionTemplateId.toString();
	return jsonTaskData;
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

function addNewSessionDialogMethod(){
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

function createNewSessionDialogItems(ajaxResponse){
	if($('#recordedTable')){
		$('.recordedTable').remove();
	}
	$('#UploadFileForm').show()
	
	var sessionTemplates = ajaxResponse.taskResponse.sessionTemplates;
	if(!this.firstTime){
		this.firstTime = true;
		$('#addNewSessionDialog').append(
			$('<div></div>')
				.text('Add new session')
				.css('font-size', '18px')
				.css('text-align', 'center')
				.css('margin-top', '10px')
			).append(
				$('#UploadFileForm')
				.append(
					$('<input>')
						.attr('id', 'UploadFileChooseFile')
						.attr('type', 'file')
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
				)
			)
			.append(
					$('<table></table>')
						.attr('id', 'basicTable')
				)
			addComboBoxData(sessionTemplates)
			addBasicTableColumnAndData(sessionTemplates[0])
		}
	
	    var select = $('#comboBox');
	    select.bind("click", function (e) {
	    	var selectedRecordId = e.target.value
	    	var selectedRecordData = getSelectedRecordData(selectedRecordId, sessionTemplates)
	    	addBasicTableColumnAndData(selectedRecordData)
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
						createNewMeasurementTable(data);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						//alert('Error: ' + textStatus + ' ' + errorThrown.message);
					}
				});
		    	$("#UploadFileChooseFile").replaceWith($("#UploadFileChooseFile").clone());
		    	$("#UploadFileSubmit").attr('disabled', true);
			}
		})
		$('#mask').fadeIn();
    	$("#UploadFileSubmit").attr('disabled', false);
		$('#addNewSessionDialog').show();
}
function createNewMeasurementTable(data){
	$('.recordedTableColumnHeaderTr').remove();
	$('.recordedTableRaw').remove();
	$('.recordedTableHeaderText').remove();
	var measurementInfo = data.taskResponse.measurementInfo
	var colSpanNumber = getColSpanNumber(measurementInfo)
	$('#addNewSessionDialog')
		.append(
			$('<table></table>')
			.attr('id', 'recordedTable')
			.attr('class', 'recordedTable')
			.append(
				$('<tr></tr>').append(
					$('<td colspan="' + colSpanNumber + '" class="recordedTableHeaderText">Recorded values</td>')	
				)
			)
			.append(
				$('<tr></tr>')
				.attr('id', 'recordedTableColumnHeaderTr')
				.attr('class', 'recordedTableColumnHeaderTr')
			)
		)
		var item1 = 0;
		var headerInfo;
		while(headerInfo = measurementInfo.headerInfos[item1]){
			$('#recordedTableColumnHeaderTr').append(
				$('<td class="columnText">' + headerInfo.measureName + '</br>' + '(' + headerInfo.measureUnit + ')' + '</td>')
			)
			
				var item2 = 0;
				var measurements;
				while(measurements = measurementInfo.measurements[item2]){
					$("#recordedTable").append(
						$('<tr></tr>')
							.attr('id', 'recordedTableRaw' + item2)
							.attr('class', 'recordedTableRaw')
					)
					
					$('#recordedTableRaw' + item2).append(
							$('<td class="columnsClass">' + measurements[headerInfo.measureName].toFixed(5) + '</td>')
					)
					item2++;
				}
				
			item1++;
		}
		
}
function getColSpanNumber(data){
	return data.headerInfos.length;
}
function addBasicTableColumnAndData(sessionTemplates){
	$('.columnHeaderTr').remove();
	$('.basicTableRaw').remove();
	$('.headerText').remove();
	var colSpanNumber = getColSpanNumber(sessionTemplates.measurementInfo)
	$("#basicTable")
	.append(
		$('<tr></tr>').append(
			$('<td colspan="' + colSpanNumber + '" class="headerText">Nominal Values</td>')	
		)
	)
	.append(
		$('<tr></tr>')
			.attr('id', 'columnHeaderTr')
			.attr('class', 'columnHeaderTr')
	)
	var item = 0;
	var headerInfo;
	while(headerInfo = sessionTemplates.measurementInfo.headerInfos[item]){
			$('#columnHeaderTr').append(
				$('<td class="columnText">' + headerInfo.measureName + '</br>' + '(' + headerInfo.measureUnit + ')' + '</td>')
			)
				var item1 = 0;
				var measurements;
				while(measurements = sessionTemplates.measurementInfo.measurements[item1]){
					$("#basicTable").append(
						$('<tr></tr>')
							.attr('id', 'basicTableRaw' + item1)
							.attr('class', 'basicTableRaw')
					)
					
					$('#basicTableRaw' + item1).append(
							$('<td class="columnsClass">' + measurements[headerInfo.measureName] + '</td>')
					)
					item1++;
				}
		item++;
	}
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
	var item = 0;
	while(sessionTemplates[item]){
		$('#comboBox')
			.append('<option value=' + sessionTemplates[item].sessionTemplateId + '>' + sessionTemplates[item].name + '</option>')
		item++;
	}
}

