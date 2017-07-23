<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <jsp:include page="include/angular-lib.jsp"/>
    <jsp:include page="include/angular-app.jsp"/>
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
                                    <h3>{{user.userData.bookNum}}</h3>
                                    <p>Номер залікової книжки</p>
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

            <%--My university--%>
            <div flex="50"
                 flex-xs="100"
                 class="card-wrapper">
                <md-card style="margin: 0">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">Мій університет</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-divider></md-divider>

                    <md-card-content>
                        <md-list flex>
                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.userData.group.name}}</h3>
                                    <p>Група</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.userData.group.department.longName}}</h3>
                                    <p>Кафедра</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.userData.group.department.faculty.longName}}</h3>
                                    <p>Факультет</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{user.userData.group.department.faculty.university.longName}}</h3>
                                    <p>Університет</p>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-card-content>
                </md-card>
            </div>

            <%--Headmaster--%>
            <div flex="33"
                 flex-xs="100"
                 class="card-wrapper">
                <md-card style="margin: 0">
                    <md-card-title style="position:relative;">
                        <md-card-title-text>
                            <span class="md-headline">Куратор</span>
                        </md-card-title-text>

                        <span flex></span>

                        <md-menu width="4">
                            <md-button class="md-icon-button"
                                       aria-label="More"
                                       ng-click="ctrl.openMenu($mdMenu, $event)"
                                       style="position:absolute; top: 25%; right: 0;">
                                <md-icon md-svg-icon="dots-vertical"></md-icon>
                            </md-button>

                            <md-menu-content width="3">
                                <md-menu-item>
                                    <md-button ng-click="ctrl.redial($event)">
                                        Копіювати email
                                    </md-button>
                                </md-menu-item>
                                <md-menu-item>
                                    <md-button ng-click="ctrl.checkVoicemail()">
                                        Надіслати лист
                                    </md-button>
                                </md-menu-item>
                                <md-menu-divider></md-menu-divider>
                                <md-menu-item>
                                    <md-button disabled="disabled"
                                               ng-click="ctrl.toggleNotifications()">
                                        Повідомлення
                                    </md-button>
                                </md-menu-item>
                            </md-menu-content>
                        </md-menu>
                    </md-card-title>

                    <md-divider></md-divider>

                    <md-card-content>
                        <md-list flex>
                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{userHeadMaster.firstName}} {{userHeadMaster.lastName}}</h3>
                                    <p>І'мя</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{userHeadMaster.email}}</h3>
                                    <p>Email</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{userHeadMaster.userData.phone}}</h3>
                                    <p>Телефон</p>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-card-content>
                </md-card>
            </div>

            <%--Head student--%>
            <div flex="33"
                 flex-xs="100"
                 class="card-wrapper">
                <md-card style="margin: 0">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">Староста</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-divider></md-divider>

                    <md-card-content>
                        <md-list flex>
                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{userHeadMaster.firstName}} {{userHeadMaster.lastName}}</h3>
                                    <p>І'мя</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{userHeadMaster.email}}</h3>
                                    <p>Email</p>
                                </div>
                            </md-list-item>

                            <md-list-item class="md-2-line">
                                <div class="md-list-item-text">
                                    <h3>{{userHeadMaster.userData.phone}}</h3>
                                    <p>Телефон</p>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-card-content>
                </md-card>
            </div>

            <div flex="33"
                 class="card-wrapper">
                <md-card style="margin: 0">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">Статистика</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-divider></md-divider>

                    <md-card-content>
                    </md-card-content>
                </md-card>
            </div>

            <%--Statistics--%>
            <div flex="100"
                 style="margin-left: 8px">
                <h1>Статистика</h1>
            </div>
            <div flex="33"
                 ng-controller="StatisticsController as sCtrl"
                 ng-repeat="item in statistics"
                 class="card-wrapper">
                <md-card style="margin: 0">
                    <md-card-title
                            style="color: white; z-index:1; background: {{item.colors.primaryDark}}; position:relative;"
                            md-whiteframe="4"
                            ng-mouseenter="item.showSettingsButton = true"
                            ng-mouseleave="item.showSettingsButton = false"
                            layout-align="center">
                        <md-card-title-text>
                            <span class="md-headline">{{item.title}}</span>
                        </md-card-title-text>

                        <md-menu>
                            <md-button class="md-icon-button"
                                       ng-click="ctrl.openMenu($mdMenu, $event)"
                                       style="position: absolute; right: 8px; top: 25%;"
                                       ng-show="item.showSettingsButton"
                                       ng-click="item.showSettings = !item.showSettings">
                                <md-icon md-svg-icon="settings"></md-icon>
                            </md-button>

                            <md-menu-content width="4">
                                <md-menu-item>
                                    <md-button ng-click="toggleGrid(item)"
                                               ng-if="item.options.showGrid">
                                        Сховати сітку
                                    </md-button>
                                    <md-button ng-click="toggleGrid(item)"
                                               ng-if="!item.options.showGrid">
                                        Показати сітку
                                    </md-button>
                                </md-menu-item>

                                <md-menu-item>
                                    <md-button disabled="disabled">
                                        Експорт
                                    </md-button>
                                </md-menu-item>
                            </md-menu-content>
                        </md-menu>

                    </md-card-title>

                    <md-card-content style="position:relative; background: {{item.colors.primary}}">
                        <canvas class="chart chart-line"
                                style="margin-top: 8px"
                                ng-if="item.data"
                                chart-data="item.data"
                                chart-labels="item.labels"
                                chart-series="item.series"
                                chart-options="item.options"
                                chart-colors="['#ffffff','#FFEB3B','#9E9E9E']"
                                chart-dataset-override="item.datasetOverride"
                                chart-click="item.onClick">
                        </canvas>
                    </md-card-content>
                </md-card>
            </div>
        </md-content>
    </div>
</div>

<script type="text/javascript">
    app.controller('AppCtrl', function ($scope) {
        $scope.user = ${user};
        $scope.userHeadMaster = ${userHeadMaster};

        this.openMenu = function ($mdMenu, ev) {
            $mdMenu.open(ev);
        };

        $scope.toggleGrid = function (item) {
            item.options.showGrid = !item.options.showGrid;

            item.options.scales.xAxes[item.options.scales.xAxes.length - 1].gridLines.display = item.options.showGrid;
            item.options.scales.yAxes[item.options.scales.yAxes.length - 1].gridLines.display = item.options.showGrid;
        };

        $scope.statistics = [
            {
                'title': 'Тиждень',
                'colors': {'primary': '#673AB7', 'primaryDark': '#4527A0'},
                'labels': ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"],
                'data': [[2, 4, 0, 5, 0, 0, 20]],
                'series': ['Пройдено тестів'],
                'onClick': function (points, evt) {
                    console.log(points, evt);
                },
                'datasetOverride': [{yAxisID: 'y-axis-1'}],
                'options': {
                    showGrid: true,
                    scales: {
                        xAxes: [],
                        yAxes: [
                            {
                                id: 'y-axis-1',
                                type: 'linear',
                                display: true,
                                position: 'left'
                            }
                        ]
                    }
                }
            },
            {
                'title': 'Місяць',
                'colors': {'primary': '#2196F3', 'primaryDark': '#1565C0'},
                'labels': ["1 тиж", "2 тиж", "3 тиж", "4 тиж"],
                'series': ['Пройдено тестів'],
                'data': [[30, 12, 43, 18]],
                'onClick': function (points, evt) {
                    console.log(points, evt);
                },
                'datasetOverride': [{yAxisID: 'y-axis-1'}, {yAxisID: 'y-axis-2'}],
                'options': {
                    showGrid: true,
                    scales: {
                        xAxes: [],
                        yAxes: [
                            {
                                id: 'y-axis-1',
                                type: 'linear',
                                display: true,
                                position: 'left'
                            }
                        ]
                    }
                }
            },
            {
                'title': 'Одногрупники',
                'colors': {'primary': '#009688', 'primaryDark': '#00695C'},
                'labels': ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"],
                'series': ['Я', 'Пономар', 'Авраменко'],
                'data': [[2, 4, 0, 5, 0, 0, 20], [5, 7, 16, 4, 0, 0, 0], [23, 11, 6, 10, 0, 0, 1]],
                'onClick': function (points, evt) {
                    console.log(points, evt);
                },
                'datasetOverride': [{yAxisID: 'y-axis-1'}],
                'options': {
                    showGrid: true,
                    scales: {
                        xAxes: [],
                        yAxes: [
                            {
                                id: 'y-axis-1',
                                type: 'linear',
                                display: true,
                                position: 'left'
                            }
                        ]
                    }
                }
            }
        ];

        for (var i = 0; i < $scope.statistics.length; i++) {
            $scope.statistics[i].options.scales.xAxes.push({gridLines: {display: $scope.statistics[i].options.showGrid}});
            $scope.statistics[i].options.scales.yAxes.push({gridLines: {display: $scope.statistics[i].options.showGrid}});
        }
    });
</script>
</body>
</html>