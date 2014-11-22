'use strict';

covoitApp.controller('addUserCtrl', ['$scope', 'ngDialog', 'daoEvents', '$rootScope', function($scope, ngDialog, daoEvents, $rootScope) {

	$scope.clickAddPassenger = function () {
		ngDialog.open({template: "view/addPassenger_dialog.html", scope: $scope});
	};

	$scope.clickAddDriver = function () {
		ngDialog.open({template: "view/addDriver_dialog.html", scope: $scope});
	};

	var addUserOkCallback = function () {
		daoEvents.getEventUsers($scope.event.id, $scope.getEventUsersCallback);
		daoEvents.getEventCars($scope.event.id, $scope.getEventCarsCallback);
		ngDialog.close();
	};
	
	var addUserNoSeatsCallback = function () {
		alert("Utilisateur non ajoute - Plus de sieges disponibles");
		ngDialog.close();
	};

	$scope.validateAddPassenger = function (name) {
		if (name == undefined || name == "") {
			alert("Entrez un nom de passager correct");
		} else {
			daoEvents.addUserAsPassenger($scope.event.id, name, addUserOkCallback, addUserNoSeatsCallback);
		}
	};

	$scope.validateAddDriver = function (driverForm) {
		if (driverForm != undefined) {
			if (driverForm.name == undefined || driverForm.name == "") {
				alert("Entrez un nom de conducteur correct");
			} else if (driverForm.carModel == undefined || driverForm.carModel == "") {
				alert("Entrez un modele de voiture correct");
			} else if (driverForm.carSeats == undefined || driverForm.carSeats < 1) {
				alert("Entrez un nombre de sieges correct");
			} else {
				daoEvents.addUserAsDriver($scope.event.id, driverForm.name, driverForm.carModel, driverForm.carSeats, addUserOkCallback, addUserNoSeatsCallback);
			}			
		}
	};

}]);