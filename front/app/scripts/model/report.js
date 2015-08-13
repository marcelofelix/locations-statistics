'use strict';

var app = angular.module('locationApp');

app.factory('reportModel', function($http, ReportService, growl) {
	var that = {
		reports: [],
		loadReports: function() {
			return ReportService.loadReports()
				.then(function(data) {
					that.reports = data;
					return data;
				});
		},

		schedule: function() {
			return ReportService.schedule()
				.then(function() {
					growl.success('Relatório criado com sucesso!');
					return that.loadReports();
				}, function() {
					growl.error('Não foi possível criar um relatório');
				});
		},

		process: function(id) {
			return ReportService.process(id)
				.then({}, function() {
					growl.error('Não foi possível processar o relatório');
				});
		}
	};
	return that;
});