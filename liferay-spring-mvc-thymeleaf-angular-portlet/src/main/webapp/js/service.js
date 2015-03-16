angular.module('angularApp.services', [])
    .factory('personService', ['$http', function($http) {

        var api = {};

        api.getPersons = function() {
            return $http({
                method: 'GET',
                url: restUrl
            });
        };

        api.addPerson = function(person) {
            return $http({
                method: 'POST',
                url: restUrl,
                data: person
            });
        };

        return api;
    }]);