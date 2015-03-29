angular.module(angularApp + '.controllers')
    .controller('personController', ['personService', function (personService) {
        var thiz = this;
        thiz.persons = [];
        personService.getPersons().success(function (response) {
            thiz.persons = response;
        });

        thiz.addPerson = function () {
            var person = {};
            person.id = this.persons.length + 1;
            person.firstname = "DemoFirstname" + person.id;
            person.lastname = "DemoLastname" + person.id;
            person.lastname = "DemoLastname" + person.id;
            personService.addPerson(person).success(function (response) {
                thiz.persons.push(person);
            });
        };
    }]);