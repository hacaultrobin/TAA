'use strict';

var covoitApp = angular.module('covoitApp', ['ui.router', 'ngDialog', 'ngQuickDate', function ($httpProvider) {
	
}]);

covoitApp.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
	
	// For any unmatched url, redirect to /events
	$urlRouterProvider.otherwise("/events");

	// Now set up the states
	$stateProvider.state('events', {
		url: "/events",
		templateUrl: "view/events.html"
	}).state('events.item', {
		url: "/{eventId}",
		templateUrl: "view/event.html"
	});
});