<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <jsp:include page="jsp_include/angular-lib.jsp"/>
    <jsp:include page="jsp_include/angular-app.jsp"/>
</head>

<style>
    .avatar {
        width: 100%;
        height: auto;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        cursor: pointer;
    }

    .image-cropper-small {
        width: 36px;
        height: 36px;
        position: relative;
        overflow: hidden;
        border-radius: 50%;
    }

    .image-cropper-middle {
        width: 48px;
        height: 48px;
        min-height: 48px;
        min-width: 48px;
        position: relative;
        overflow: hidden;
        border-radius: 50%;
    }

    .image-cropper-large {
        height: 56px;
        min-height: 70px;
        width: 56px;
        min-width: 70px;
        position: relative;
        overflow: hidden;
        border-radius: 50%;
    }

    .image-cropper-large-bordered {
        height: 56px;
        min-height: 70px;
        width: 56px;
        min-width: 70px;
        position: relative;
        overflow: hidden;
        border-radius: 50%;
        border: solid white;
    }

    .card-wrapper {
        padding: 8px;
    }
</style>

<body ng-app="BaseApp"
      ng-cloak>

<div layout="column"
     flex
     layout-fill
     ng-cloak
     ng-controller="AppCtrl as ctrl"
     md-colors="background">
    <%--Toolbars--%>
    <md-toolbar md-whiteframe="4"
                style="padding-left:8px; z-index: 3">
        <div class="md-toolbar-tools">
            <div flex layout="row" layout-align="start center">
                <span class="md-headline" style="color: #2196F3">Futoria</span>
                <span>&nbsp;</span>
                <span class="md-headline"
                      style="color: #757575; font-family: RobotoLight; font-weight: bolder">Tests</span>

                <md-autocomplete
                        style="margin-left:16px;"
                        flex="40"
                        hide-xs
                        md-selected-item="ctrl.selectedItem"
                        md-search-text-change="ctrl.searchTextChange(ctrl.searchText)"
                        md-search-text="ctrl.searchText"
                        md-selected-item-change="ctrl.selectedItemChange(item)"
                        md-items="item in ctrl.querySearch(ctrl.searchText)"
                        md-item-text="item.display"
                        md-min-length="0"
                        placeholder="Введіть пошуковий запит...">
                    <md-item-template>
                        <span md-highlight-text="ctrl.searchText" md-highlight-flags="^i">{{item.display}}</span>
                    </md-item-template>
                    <md-not-found>
                        No states matching "{{ctrl.searchText}}" were found.
                        <a ng-click="ctrl.newState(ctrl.searchText)">Create a new one!</a>
                    </md-not-found>
                </md-autocomplete>
            </div>

            <%--<h2 flex md-truncate> Welcome, ${pageContext.request.userPrincipal.name}</h2>--%>
            <md-menu md-position-mode="target-right target">
                <md-button class="md-icon-button"
                           aria-label="Modules"
                           ng-click="ctrl.openMenu($mdMenu, $event)">
                    <md-icon md-svg-icon="apps" style="fill: #757575;!important;"></md-icon>
                </md-button>

                <md-menu-content width="4"
                                 layout="column"
                                 layout-align="center stretch">
                    <md-menu-item>
                        <div layout="column"
                             ng-init="h=1;"
                             ng-mouseenter="h=4;"
                             ng-mouseleave="h=1;"
                             md-whiteframe="{{h}}"
                             layout-align="center center"
                             ng-repeat="item in [1,2,3]">
                            <img src="https://maxcdn.icons8.com/Share/icon/Logos//google_logo1600.png"
                                 style="max-height: 56px; max-width: 56px"/>
                            <span class="md-caption">Module {{item}}</span>
                        </div>
                    </md-menu-item>

                    <md-menu-item>
                    </md-menu-item>

                    <md-menu-item>
                        <div layout="column"
                             ng-init="h=1;"
                             ng-mouseenter="h=4;"
                             ng-mouseleave="h=1;"
                             md-whiteframe="{{h}}"
                             layout-align="center center"
                             ng-repeat="item in [4,5,6]">
                            <img src="https://maxcdn.icons8.com/Share/icon/Logos//google_logo1600.png"
                                 style="max-height: 56px; max-width: 56px"/>
                            <span class="md-caption">Module {{item}}</span>
                        </div>
                    </md-menu-item>

                    <md-menu-item>
                    </md-menu-item>

                    <md-menu-item>
                        <md-button ng-click="null"
                                   class="md-accent">
                            Futoria - домашня
                        </md-button>
                    </md-menu-item>
                </md-menu-content>
            </md-menu>

            <md-button class="md-icon-button"
                       aria-label="Settings">
                <md-icon md-svg-icon="bell"
                         style="fill: #757575;!important;"></md-icon>
            </md-button>

            <md-button class="md-icon-button"
                       aria-label="Settings">
                <md-icon md-svg-icon="account"
                         style="fill: #757575;!important;"></md-icon>
            </md-button>
        </div>
    </md-toolbar>

    <%--Main content--%>
    <div layout="row"
         layout-fill
         flex>
        <%--Side navigation--%>
        <div layout="column">
            <jsp:include page="template/sidenav.jsp"/>
        </div>

        <%--Content--%>
        <md-content layout="row"
                    flex
                    layout-align="space-between start"
                    layout-wrap>

            <%--My info--%>
            <div flex="50"
                 flex-xs="100"
                 class="card-wrapper">
                <md-card style="margin: 0">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">Моя інформація</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-divider></md-divider>

                    <md-card-content>
                        <md-list flex>
                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.lastName}} {{user.firstName}} {{user.middleName}}</h3>
                                    <p>ПІБ</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.email}}</h3>
                                    <p>Email</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.userData.phone}}</h3>
                                    <p>Номер телефону</p>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-card-content>
                </md-card>
            </div>
        </md-content>
    </div>
</div>

<script type="text/javascript">
    app.controller('AppCtrl', function ($scope) {
        $scope.user = ${user};

        this.openMenu = function ($mdMenu, ev) {
            $mdMenu.open(ev);
        };
    });
</script>
</body>
</html>