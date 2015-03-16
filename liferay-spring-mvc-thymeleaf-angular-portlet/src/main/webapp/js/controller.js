angular.module('angularApp.controllers', [])
    .controller('personController', ['$scope', 'personService', function($scope, personService) {
        $scope.persons = [];
        personService.getPersons().success(function (response) {
            $scope.persons = response;
        });

        $scope.addPerson = function() {
            var person = {};
            person.id = $scope.persons.length;
            person.firstname = "DemoFirstname" + $scope.persons.length;
            person.lastname = "DemoLastname" + $scope.persons.length;
            personService.addPerson(person).success(function (response) {
                $scope.persons.push(person);
            });
        };
    }]);