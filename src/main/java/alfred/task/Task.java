package alfred.task;

public class Task {
        public String description;
        private boolean isDone;


        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }
        public String getDescription() {

        return description;

        }
        public boolean isDone() {
            return isDone;
        }



        public void markAsDone() {
            isDone = true;
        }

        public void markAsNotDone() {
            isDone = false;
        }

        public String getIcon() {
            return isDone ? "X" : " ";
        }

        @Override
        public String toString() {
            return "[" + getIcon() + "] " + description;
        }

}
