$(function() {

})

function updatePreviewGrids(ajaxResponse) {
	$('#previewGridPanelCenter').show();
	$('#previewGridPanelEast').show();
	var nominalGridPreviewObject = createGridColumns(ajaxResponse.taskResponse.sessionTemplateInfo.headerInfos, 80);
	var recordedGridPreviewObject = createGridColumns(ajaxResponse.taskResponse.measurementInfo.headerInfos, 150);
	$('#recordedGridPreview').datagrid({
		width: recordedGridPreviewObject.gridWidth,
		remoteSort : false,
		singleSelect : true,
		nowrap : false,
		border:false,
        scrollbarSize:0,
		fitColumns : true,
		columns : [recordedGridPreviewObject.gridColumns],
	});
	$('#nominalGridPreview').datagrid({
		width: nominalGridPreviewObject.gridWidth,
		remoteSort : false,
		singleSelect : true,
		nowrap : false,
        scrollbarSize:0,
		fitColumns : true,
		columns : [nominalGridPreviewObject.gridColumns],
	});
	$('#nominalGridPreview').datagrid('loadData', ajaxResponse.taskResponse.sessionTemplateInfo.measurements)
	$('#recordedGridPreview').datagrid('loadData', ajaxResponse.taskResponse.measurementInfo.measurements)
	
}

function createGridColumns(headerInfos, columnWidth) {
	var gridColumns = [];
	var item = 0;
	var headerInfo;
	var gridWidth = 0;
	while (headerInfo = headerInfos[item]) {
		var currentHeaderTextWidth = headerInfo.measureName.length * 8;
		currentHeaderTextWidthToSet = currentHeaderTextWidth > columnWidth ? currentHeaderTextWidth : columnWidth
		var widthToGrid = currentHeaderTextWidth > columnWidth ? currentHeaderTextWidth : columnWidth
		var currentHeader = {
			width: currentHeaderTextWidthToSet.toString(),
			field : headerInfo.measureName,
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
