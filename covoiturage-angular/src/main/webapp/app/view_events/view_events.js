'use strict';

covoitApp.controller('eventsCtrl', ['$scope', function($scope) {
	
  $scope.content = "Liste d'events";
  
  // Créer un service (dao) qui fait une requete Ajax vers le service REST /events
  // exemple : daoEvents.getAllEvents(callback)
  
}]);