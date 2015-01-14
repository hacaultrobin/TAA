'use strict';

covoitApp.controller('addEventCtrl', ['$scope', 'ngDialog', 'daoEvents', '$rootScope',  function($scope, ngDialog, daoEvents, $rootScope) {
		
	$scope.validateAddEvent = function(eventForm) {
		if (eventForm != undefined) {
			if (eventForm.place == undefined || eventForm.place == "") {
				alert("Entrez un lieu correct");
			} else if (eventForm.date == undefined) {
				alert("Choisissez une date");
			} else if (eventForm.desc == undefined || eventForm.desc == "") {
				alert("Entrez une description correcte");
			} else {				
				daoEvents.addEvent(eventForm.date.toUTCString(), eventForm.place, eventForm.desc, function () {
					ngDialog.close();
					$rootScope.$broadcast("reloadEventsList");
				});
			}
		}
	};

}]);
