$(document).ready(function() {
});
$(function() {
	$('#rightPanelEmptyText')
		.text('In order to view sessions, please select device')
		.css('text-align', 'center')
		.css('margin-top', '80px')
		.css('font-size', '28px')
	$("#tree").dynatree({
		clickFolderMode : 1,
		children : [{
			isFolder : true,
			title : 'Cities',
			id: 0,
			children: [{}]
		}],
		onClick : function(node, a) {
			var eventTargetType = node.getEventTargetType(event);
			if(eventTargetType != 'expander' && node.getLevel() != 1){
				var level = node.getLevel();
				var task = "";
				switch (level)
				{
				case 2:
				  task = "getFacility";
				  break;
				case 3:
				  task = "getDepartment";				  
				  break;
				case 4:
				  task = "getRoom";			  
				  break;
				case 5:
				  task = "getDevice";			 
				  break;
				}
				var id = node.data.id;
				var jsonData = {};
				jsonData.taskName = task;
				var jsonTaskData = {};
				jsonTaskData.id = id;
				$.ajax({
					type : "GET",
					url : 'task.is',
					data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
					dataType : "json",
					success : function(data) {
						creteAvailableActions(data.availableTasks);
						$('#leftBottomPanel').show();
						createBasicInfo(data);
						if(level == '5'){
							updateNewMainGrid(data);
						}else{
							$('#mainGridSessionsDiv').hide();
							$('#previewGridPanelCenter').hide();
							$('#previewGridPanelEast').hide();
							$('#rightPanelEmptyText').show()
						}
					}
				})
			}else{
				if(eventTargetType != 'expander'){
					var availableTask = ['addNewFacility']
					creteAvailableActions(availableTask);
					$('#leftBottomPanel').hide();
					$('#mainGridSessionsDiv').hide();
					$('#previewGridPanelCenter').hide();
					$('#previewGridPanelEast').hide();
					$('#rightPanelEmptyText').show()
				}
			}
		},
		onExpand : function(flag, node) {
			node.removeChildren();
			if(flag){
				var level = node.getLevel();
				var task = "";
				switch (level)
				{
					case 1:
					  task = "getFacilities";
					  break;
					case 2:
					  task = "getDepartments";				  
					  break;
					case 3:
					  task = "getRooms";			  
					  break;
					case 4:
					  task = "getDevices";			 
					  break;
				} 
			var id = node.data.id;	
			var jsonData = {};
			jsonData.taskName = task;
			var jsonTaskData = {};
			jsonTaskData.parentId = id;
			$.ajax({
				type : "GET",
				url : 'task.is',
				data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
				dataType : "json",
				success : function(data) {
					var item = 0;
					while(data.taskResponse.entities && data.taskResponse.entities[item]){
						var currentEntities = data.taskResponse.entities[item]
						node.addChild({
							title: currentEntities.name,
							id: currentEntities.id,
							children: level == 4 ? null : [{}],
							isFolder : level == 4 ? false : true
						});
						item++;
					}
				}
			})
			}else{
				node.addChild({
					children: [{}],
			    });
			}
		}
	});
});

function createBasicInfo(ajaxResponse){
	var item = 0;
	var entity;
	$('.basicInfoFields').remove();
	for(entity in ajaxResponse.taskResponse.entity){
		var localizedValue = getLocale(entity);
		var displayValue = localizedValue ? localizedValue : entity;
		$('#leftBottomPanel')
			.append(
				$('<div></div>')
					.attr('class', 'basicInfoFields')
					.css('font-size', '13px')
					.css('margin-top', '10px')
					.text(displayValue + ': ' + ajaxResponse.taskResponse.entity[entity])
			)
		}
};

function updateNewMainGrid(ajaxResponse){
	$('#rightPanelEmptyText').hide();
	$('#mainGridSessionsDiv').show();
	var mainGridSessionsObject = createMainGridColumns(ajaxResponse.taskResponse.headerInfos);
	$('#mainGridSessions').datagrid({
		onClickRow: function(rowIndex, rowData){
		    $('#downloadReportButton').linkbutton('enable');
			var jsonData = {};
			jsonData.taskName = 'getSession';
			var jsonTaskData = {};
			jsonTaskData.id = rowData.sessionId;
			$.ajax({
				type : "GET",
				url : 'task.is',
				data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
				dataType : "json",
				success : function(ajaxResponse) {
					creteAvailableActions(ajaxResponse.availableTasks, true)
					updatePreviewGrids(ajaxResponse)
				}
			})
		},
		width: mainGridSessionsObject.gridWidth,
        scrollbarSize:0,
		remoteSort : false,
		singleSelect : true,
		fitColumns : true,
		columns : [mainGridSessionsObject.gridColumns],
	});
	$('#mainGridSessions').datagrid('loadData', ajaxResponse.taskResponse.sessions)
}

function createMainGridColumns(headerInfos) {
	var gridColumns = [];
	var item = 0;
	var headerInfo;
	var gridWidth = 0;
	while (headerInfo = headerInfos[item]) {
		var localizeHeader = getLocale(headerInfo)
		var columnWidth = getColumnWidthForMainGrid(localizeHeader)
		var currentHeader = {
			width: columnWidth,
			field : headerInfo == 'sessionPerformDate' ? 'sessionPerformedDate' : headerInfo,
			title : localizeHeader ? localizeHeader : headerInfo,
			formatter: function(value,row,index){
				if (row.sessionPerformedDate && row.sessionPerformedDate == value){
					var value = $.datepicker.formatDate('dd-mm-yy', new Date(value))
					return value;
				} else {
					return value;
				}
				return value;
			},
			align : 'center',
			sortable : true
		}
		gridWidth = gridWidth + columnWidth;
		gridColumns.push(currentHeader)
		item++;
	}
	return gridObject = {
			gridColumns:gridColumns,
			gridWidth: gridWidth + 20
		}
}

function getColumnWidthForMainGrid(string){
	var stringLengthPix = string.length * 8;
	if(string == 'Session Template'){
		stringLengthPix = 250;
	}else{
		if(string == 'Status'){
			stringLengthPix = 100;
		}
	}
	return stringLengthPix;
}

function createSessionTables(ajaxResponse){
	var measurementInfo = ajaxResponse.taskResponse.measurementInfo;
	var sessionTemplateInfo = ajaxResponse.taskResponse.sessionTemplateInfo;
	var colSpanFromMeasurementInfo = getColSpanNumber(measurementInfo)
	var colSpanFromSessionTemplateInfo = getColSpanNumber(sessionTemplateInfo)
	$('.sessionTemplateInfoGrid').remove();
	$('.measurementInfoGrid').remove();
	$('#rightBottomPanel')
		.append(
			$('<table></table>')
				.attr('id', 'sessionTemplateInfoGrid')
				.attr('class', 'sessionTemplateInfoGrid')
				.append(
					$('<tr></tr>')
					.attr('id', 'sessionTemplateInfoGridHeaderTitle')
					.append(
						$('<td colspan="' + colSpanFromSessionTemplateInfo + '" class="headerText">Nominal Values</td>')	
					)
			)
		)
		.append(
			$('<table></table>')
				.attr('id', 'measurementInfoGrid')
				.attr('class', 'measurementInfoGrid')
				.append(
					$('<tr></tr>')
					.attr('id', 'measurementInfoGridHeaderTitle')
					.append(
						$('<td colspan="' + colSpanFromMeasurementInfo + '" class="headerText">Recorded values</td>')	
					)
			)
		)
	$('#sessionTemplateInfoGrid')
	.append(
		$('<tr></tr>')
		.attr('id', 'sessionTemplateInfoGridColumnHeaderTr')
		.attr('class', 'sessionTemplateInfoGridColumnHeaderTr')
	)
    var item = 0;
	var headerInfo;
	while(headerInfo = sessionTemplateInfo.headerInfos[item]){
		$('#sessionTemplateInfoGridColumnHeaderTr').append(
			$('<td class="columnText">' + headerInfo.measureName + '</br>' + '(' + headerInfo.measureUnit + ')' + '</td>')
		)
		var item1 = 0;
		var measurements;
		while(measurements = sessionTemplateInfo.measurements[item1]){
			$("#sessionTemplateInfoGrid").append(
				$('<tr></tr>')
					.attr('id', 'sessionTemplateInfoGridRaw' + item1)
					.attr('class', 'sessionTemplateInfoGridRaw')
			)
			$('#sessionTemplateInfoGridRaw' + item1).append(
					$('<td class="columnsClass">' + measurements[headerInfo.measureName] + '</td>')
			)
			item1++;
		}
		item++;
	}
	
	$('#measurementInfoGrid')
	.append(
		$('<tr></tr>')
		.attr('id', 'measurementInfoGridColumnHeaderTr')
		.attr('class', 'measurementInfoGridColumnHeaderTr')
	);
	var item1 = 0;
	var headerInfo;
	while(headerInfo = measurementInfo.headerInfos[item1]){
		$('#measurementInfoGridColumnHeaderTr').append(
			$('<td class="columnText">' + headerInfo.measureName + '</br>' + '(' + headerInfo.measureUnit + ')' + '</td>')
		)
		item2 = 0;
		var measurements;
		while(measurements = measurementInfo.measurements[item2]){
			$("#measurementInfoGrid").append(
				$('<tr></tr>')
					.attr('id', 'measurementInfoGridRaw' + item2)
					.attr('class', 'measurementInfoGridRaw')
			)
			$('#measurementInfoGridRaw' + item2).append(
					$('<td class="columnsClass">' + measurements[headerInfo.measureName] + '</td>')
			)
			item2++;
		}
		item1++;
	}
}

function getColSpanNumber(data){
	return data.headerInfos.length;
}
