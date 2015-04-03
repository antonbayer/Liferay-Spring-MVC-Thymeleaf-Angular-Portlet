var angularApp = 'angularApp';

angular.module(angularApp, [
    angularApp + '.directives',
    angularApp + '.controllers',
    angularApp + '.services',
    angularApp + '.factories'
]).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}]);

angular.module(angularApp + '.directives', []);
angular.module(angularApp + '.controllers', []);
angular.module(angularApp + '.services', []);
angular.module(angularApp + '.factories', []);