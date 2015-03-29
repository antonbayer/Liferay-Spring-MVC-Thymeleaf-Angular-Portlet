var angularApp = 'angularApp';

angular.module(angularApp, [
    angularApp + '.directives',
    angularApp + '.controllers',
    angularApp + '.services'
]);

angular.module(angularApp + '.directives', []);
angular.module(angularApp + '.controllers', []);
angular.module(angularApp + '.services', []);