angular.module('angularApp.controllers', [])
    .controller('personController', ['$scope', 'personService', function($scope, personService) {
        $scope.persons = [];
        personService.getPersons().success(function (response) {
            $scope.persons = response;
        });

        $scope.addPerson = function() {
            var person = {};
            person.id = $scope.persons.length + 1;
            person.firstname = "DemoFirstname" + person.id;
            person.lastname = "DemoLastname" + person.id;
            person.lastname = "DemoLastname" + person.id;
            personService.addPerson(person).success(function (response) {
                $scope.persons.push(person);
            });
        };
    }]);