angular.module('angularApp.services', [])
    .factory('personService', ['$http', function($http) {

        var api = {};

        api.getPersons = function() {
            return $http({
                method: 'GET',
                url: resourceURL
            });
        };

        api.addPerson = function(person) {
            return $http({
                method: 'POST',
                url: resourceURL,
                data: person
            });
        };

        return api;
    }]);