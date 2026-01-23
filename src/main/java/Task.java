public class Task {
        public String description;
        public boolean completed;

        public Task(String description) {
            this.description = description;
            this.completed = false;
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
