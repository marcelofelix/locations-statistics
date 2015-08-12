'use strict';

/**
 * @ngdoc overview
 * @name locationApp
 * @description
 * # locationApp
 *
 * Main module of the application.
 */
angular
  .module('locationApp', [
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'treeGrid',
    'angular-growl'
  ])
  .config(function($routeProvider, $httpProvider, growlProvider) {
    $httpProvider.defaults.timeout = 3000;
    growlProvider.globalTimeToLive(3000);
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/:id/result', {
        templateUrl: 'views/result.html',
        controller: 'ResultCtrl',
        resolve: {
          result: function(ReportService, $route, $location, growl) {
            return ReportService.loadReportResult($route.current.pathParams.id)
              .then({}, function() {
                growl.error('Erro ao carregar o resultado');
                $location.path('/');
                return [];
              });
          }
        }
      })
      .otherwise({
        redirectTo: '/'
      });
  });