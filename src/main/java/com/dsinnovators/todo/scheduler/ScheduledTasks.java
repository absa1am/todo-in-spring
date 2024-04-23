package com.dsinnovators.todo.scheduler;

import com.dsinnovators.todo.entities.Task;
import com.dsinnovators.todo.enums.Status;
import com.dsinnovators.todo.services.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasks {

    private TaskService taskService;

    public ScheduledTasks(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateTaskStatus() {
        List<Task> tasks = taskService.getTasks();

        for (Task task : tasks) {
            if (task.getEndDate().isBefore(LocalDate.now()) && task.getStatus() != Status.DONE) {
                task.setStatus(Status.BACKLOG);

                taskService.updateTask(task);
            }
        }
    }

}
