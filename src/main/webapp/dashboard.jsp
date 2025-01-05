<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Dashboard - Todo App</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <style>
        .task-space.active {
            background-color: #e5e7eb;
        }
        .task {
            transition: all 0.3s ease;
        }
        .task.completed {
            background-color: #f3f4f6;
            text-decoration: line-through;
        }
        .task-input {
            background-color: #f9fafb;
        }
    </style>
</head>
<body class="bg-gray-100">
    <div class="flex h-screen">
        <!-- Left Sidebar - TaskSpaces -->
        <div class="w-1/4 bg-white border-r border-gray-200 p-4">
            <div class="flex justify-between items-center mb-6">
                <h2 class="text-xl font-bold">Task Spaces</h2>
                <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600" onclick="showCreateDialog()">
                    <i class="fas fa-plus mr-2"></i> New
                </button>
            </div>
            <div th:each="taskSpace : ${taskSpaces}">
                <div class="task-space p-3 rounded cursor-pointer hover:bg-gray-100" 
                     th:classappend="${taskSpace.id == currentTaskSpace.id} ? 'active' : ''"
                     th:onclick="|loadTaskSpace(${taskSpace.id})|">
                    <h3 class="font-medium" th:text="${taskSpace.title}"></h3>
                    <p class="text-sm text-gray-600" th:text="${taskSpace.description}"></p>
                </div>
            </div>
        </div>

        <!-- Main Content - Tasks -->
        <div class="flex-1 p-6">
            <div class="max-w-3xl mx-auto">
                <!-- Current TaskSpace Info -->
                <div class="mb-6">
                    <h1 class="text-2xl font-bold mb-2" th:text="${currentTaskSpace.title}"></h1>
                    <p class="text-gray-600" th:text="${currentTaskSpace.description}"></p>
                </div>

                <!-- Tabs -->
                <div class="mb-6 border-b border-gray-200">
                    <nav class="-mb-px flex space-x-8">
                        <a href="#" th:class="|${activeTab == 'active'} ? 'tab-active border-blue-500 text-blue-600' : 'text-gray-500 hover:text-gray-700'|"
                           onclick="showActiveTasks()">Active Tasks</a>
                        <a href="#" th:class="|${activeTab == 'completed'} ? 'tab-active border-blue-500 text-blue-600' : 'text-gray-500 hover:text-gray-700'|"
                           onclick="showCompletedTasks()">Completed</a>
                        <a href="#" th:class="|${activeTab == 'deleted'} ? 'tab-active border-blue-500 text-blue-600' : 'text-gray-500 hover:text-gray-700'|"
                           onclick="showDeletedTasks()">Deleted</a>
                    </nav>
                </div>

                <!-- New Task Input -->
                <div class="mb-6">
                    <form action="addTask" method="POST">
                        <div class="flex gap-4">
                            <input type="text" id="newTaskInput" name="newTaskContent"
                                   class="flex-1 p-3 border border-gray-300 rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                                   placeholder="Add a new task..."/>
                            <button type="submit" class="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600">Add Task</button>
                        </div>
                    </form>
                </div>

                <!-- Tasks List -->
                <div id="activeTasks" class="space-y-3">
                    <div th:each="task : ${activeTasks}">
                        <div class="task flex items-center p-4 bg-white rounded-lg shadow" th:classappend="${task.completed} ? 'completed' : ''">
                            <input type="checkbox" th:checked="${task.completed}" onclick="completeTask(${task.id})" class="mr-4 h-5 w-5 text-blue-600"/>
                            <span class="flex-1" th:text="${task.content}"></span>
                            <div class="flex items-center space-x-3">
                                <button th:onclick="|editTask(${task.id})|" class="text-gray-500 hover:text-blue-500">
                                    <i class="fas fa-pencil"></i>
                                </button>
                                <button th:onclick="|deleteTask(${task.id})|" class="text-gray-500 hover:text-red-500">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Completed Tasks -->
                <div id="completedTasks" class="hidden space-y-3">
                    <div th:each="task : ${completedTasks}">
                        <div class="task completed flex items-center p-4 bg-white rounded-lg shadow">
                            <span class="flex-1" th:text="${task.content}"></span>
                            <span class="text-sm text-gray-500">Completed on <span th:text="${task.lastModifiedDt}"></span></span>
                        </div>
                    </div>
                </div>

                <!-- Deleted Tasks -->
                <div id="deletedTasks" class="hidden space-y-3">
                    <div th:each="task : ${deletedTasks}">
                        <div class="task deleted flex items-center p-4 bg-white rounded-lg shadow">
                            <span class="flex-1" th:text="${task.content}"></span>
                            <span class="text-sm text-gray-500">Deleted on <span th:text="${task.deletionDt}"></span></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function loadTaskSpace(id) {
            window.location.href = "loadTaskSpace?id=" + id;
        }

        function showActiveTasks() {
            document.getElementById("activeTasks").classList.remove("hidden");
            document.getElementById("completedTasks").classList.add("hidden");
            document.getElementById("deletedTasks").classList.add("hidden");
        }

        function showCompletedTasks() {
            document.getElementById("activeTasks").classList.add("hidden");
            document.getElementById("completedTasks").classList.remove("hidden");
            document.getElementById("deletedTasks").classList.add("hidden");
        }

        function showDeletedTasks() {
            document.getElementById("activeTasks").classList.add("hidden");
            document.getElementById("completedTasks").classList.add("hidden");
            document.getElementById("deletedTasks").classList.remove("hidden");
        }

        function completeTask(id) {
            // Handle task completion via AJAX or form submission
            window.location.href = "completeTask?id=" + id;
        }

        function deleteTask(id) {
            // Handle task deletion via AJAX or form submission
            window.location.href = "deleteTask?id=" + id;
        }

        function editTask(id) {
            // Handle task edit action
            window.location.href = "editTask?id=" + id;
        }
    </script>
</body>
</html>
