angular.module('angularApp.services', [])
    .service('personService', ['$http', function ($http) {
        this.getPersons = function () {
            return $http({
                method: 'GET',
                url: resourceURL
            });
        };

        this.addPerson = function (person) {
            return $http({
                method: 'POST',
                url: resourceURL,
                data: person
            });
        };
    }]);