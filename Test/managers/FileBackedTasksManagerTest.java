package managers;

class FileBackedTasksManagerTest  extends TaskManagerTest<FileBackedTasksManager> {

    @Override
    FileBackedTasksManager getManager() {
        return new FileBackedTasksManager("file_backed_tasks_manager_file_name");
    }

}