$(function() {
	jQuery("#mainGrid").jqGrid(
			{
				datatype : "local",
				height : 300,
				width : 990,
				colNames : [ 'Inv No', 'Date', 'Client', 'Amount', 'Tax',
						'Total', 'Notes' ],
				colModel : [ {
					name : 'id',
					index : 'id',
					width : 60,
					sorttype : "int"
				}, {
					name : 'invdate',
					index : 'invdate',
					width : 90,
					sorttype : "date"
				}, {
					name : 'name',
					index : 'name',
					width : 100
				}, {
					name : 'amount',
					index : 'amount',
					width : 80,
					align : "right",
					sorttype : "float"
				}, {
					name : 'tax',
					index : 'tax',
					width : 80,
					align : "right",
					sorttype : "float"
				}, {
					name : 'total',
					index : 'total',
					width : 80,
					align : "right",
					sorttype : "float"
				}, {
					name : 'note',
					index : 'note',
					width : 150,
					sortable : false
				} ]
			// multiselect: true
			});
	var mydata = [ {
		id : "1",
		invdate : "2007-10-01",
		name : "test",
		note : "note",
		amount : "200.00",
		tax : "10.00",
		total : "210.00"
	}, {
		id : "2",
		invdate : "2007-10-02",
		name : "test2",
		note : "note2",
		amount : "300.00",
		tax : "20.00",
		total : "320.00"
	}];
	for ( var i = 0; i <= mydata.length; i++) {
		jQuery("#mainGrid").jqGrid('addRowData', i + 1, mydata[i]);
	}
	;
	jQuery("#mainGrid").click(function() {
		var id = jQuery("#mainGrid").jqGrid('getGridParam', 'selrow');
		if (id) {
			var ret = jQuery("#mainGrid").jqGrid('getRowData', id);
		}
	});
})