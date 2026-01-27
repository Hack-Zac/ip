package task;

public class Task {
        public String description;
        private boolean completed;


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



        public void markAsDone() {
            completed = true;
        }

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
