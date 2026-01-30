package alfred.task;
/**
 * Represents a task with a description and completion status.
 */


public class Task {
        public String description;
        private boolean completed;

    /**
     * Creates a new task with the given description.
     *
     * @param description The task description.
     */

        public Task(String description) {
            this.description = description;
            this.completed = false;
        }
        public String getDescription() {

        return description;

        }
        public boolean isDone() {
            return completed;
        }


    /**
     * Marks the task as done.
     */

        public void markAsDone() {
            completed = true;
        }

    /**
     * Marks the task as not done.
     */


    public void markAsNotDone() {
            completed = false;
        }

        public String getIcon() {
            return completed ? "X" : " ";
        }

        @Override
        public String toString() {
            return "[" + getIcon() + "] " + description;
        }

}
