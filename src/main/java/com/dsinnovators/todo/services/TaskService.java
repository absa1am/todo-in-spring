package com.dsinnovators.todo.services;

import com.dsinnovators.todo.entities.Task;
import com.dsinnovators.todo.helpers.CSVHelper;
import com.dsinnovators.todo.repositories.TaskRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Page<Task> getTasks(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Map<String, Integer> getProgress() {
        var tasks = getTasks();
        Map<String, Integer> progress = new HashMap<>();

        progress.put("Total", 0);
        progress.put("Pending", 0);
        progress.put("Processing", 0);
        progress.put("Completed", 0);
        progress.put("Backlog", 0);

        for (var task : tasks) {
            var status = task.getStatus().value();

            progress.put("Total", progress.get("Total") + 1);
            progress.put(status, progress.get(status) + 1);
        }

        return progress;
    }

    public InputStreamResource loadCsv() throws IOException {
        List<Task> tasks = getTasks();

        return new InputStreamResource(CSVHelper.writeCsv(tasks));
    }

}
