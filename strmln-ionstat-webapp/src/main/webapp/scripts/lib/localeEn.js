function getLocale(stringToLocalize){
	var allLocalizeStrings = {
		"sessionId":'Session Id',
		"sessionPerformDate":'Session Perform Date',
		"sessionTemplate":'Session Template',
		"status":'Status',
		"deviceUsage":'Device usage',
		"id":'Id',
		"model":'Model',
		"manufacturer":'Manufacturer',
		"sessions":'Sessions',
		"serialNumber":'Serial number',
		"technician":'Technician',
		"name":'Name',
		"city":'City',
		"contactPerson":'Contact person',
		"email":'Email',
		"phone":'Phone',
		"pib":'PIB',
		"address":'Address',
		"inspectionInterval":'Inspection interval',
		"technician":'Technician'
	}
	var stringFound = false;
	for(currentString in allLocalizeStrings){
		if(currentString == stringToLocalize){
			stringFound = allLocalizeStrings[stringToLocalize]
		}
	}
	return stringFound;
}