'use strict';

var app = angular.module('locationApp');

app.factory('ReportService', function($http, $q, path) {
	return {
		loadReportResult: function(id) {
			return $http.get(path + 'report/' + id + '/result')
				.then(function(result) {
					return result.data;
				}, function(error) {
					return $q.reject(error);
				});
		},

		loadReports: function() {
			return $http.get(path + 'report')
				.then(function(result) {
					return result.data;
				}, function(error) {
					return $q.reject(error);
				});
		},

		schedule: function() {
			return $http.post(path + 'report/schedule')
				.then({}, function(error) {
					return $q.reject(error);
				});
		}
	};
});