angular.module(angularApp + '.controllers')
    .factory('httpInterceptor', ['$rootElement', '$q', function ($rootElement, $q) {
        return {
            response: function (response) {
                if (response.headers()['internal-server-exception']) {
                    angular.element($rootElement).replaceWith(response.data);
                    return $q.reject(response);
                }
                return response;
            }
        };
    }]);