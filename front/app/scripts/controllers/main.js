'use strict';

/**
 * @ngdoc function
 * @name locationApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the frontApp
 */
angular.module('locationApp')
	.controller('MainCtrl', function($scope, reportModel, $timeout) {
		reportModel.loadReports();
		$scope.model = reportModel;
		var poll = function() {
			$timeout(function() {
				console.log('Polling');
				reportModel.loadReports();
				poll();
			}, 5000);
		};
		poll();

	});