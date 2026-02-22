# Alfred

> "Why do we fall Master Wayne? So we can learn to pick ourselves up!
" - Your personal task manager

Alfred is a **desktop task management application** with a snotty English butler personality. It helps you manage your
todos, deadlines, and events with ease while you fight crime on the streets of Gotham.

![Ui](docs/Ui.png)

## Features

- **Task Management**: Add todos, deadlines, and events
- **Smart Date Formatting**: Dates displayed in proper English (e.g., "27th January 2024")
- **Optional Time Support**: Add times to deadlines and events when needed
- **Search**: Find tasks by keyword
- **Notes**: Add additional notes to any task
- **Persistent Storage**: Your tasks are saved automatically

## Quick Start

1. Ensure you have Java 17 or above installed
2. Download the latest `alfred.jar` from [Releases](../../releases)
3. Double-click the file or run `java -jar alfred.jar`
4. Type commands and press Enter

## Command Summary

| Action       | Format                                                      | Example                                                                       |
|--------------|-------------------------------------------------------------|-------------------------------------------------------------------------------|
| Add todo     | `todo <description>`                                        | `todo investigate Arkham`                                                     |
| Add deadline | `deadline <description> /by <date> [time]`                  | `deadline fight Joker /by 2024-01-27 14:00`                                   |
| Add event    | `event <description> /from <date> [time] /to <date> [time]` | `event Wayne Enterprises meeting /from 2024-01-27 09:00 /to 2024-01-27 10:00` |
| List         | `list`                                                      | `list`                                                                        |
| Mark done    | `mark <number>`                                             | `mark 1`                                                                      |
| Unmark       | `unmark <number>`                                           | `unmark 1`                                                                    |
| Delete       | `delete <number>`                                           | `delete 1`                                                                    |
| Find         | `find <keyword>`                                            | `find Gordon`                                                                 |
| Add note     | `note <number> <text>`                                      | `note meetup with Fox`                                                        |
| Exit         | `bye`                                                       | `bye`                                                                         |
| Help         | `help`                                                      | `bye`                                                                         |

## Acknowledgements

- UI design inspiration adapted from [AK-matrix/ip](https://github.com/AK-matrix/ip)
- [Butler icons](https://www.flaticon.com/free-icons/butler) created by Freepik - Flaticon
- [Bat icons](https://www.flaticon.com/free-icons/bat) created by Freepik - Flaticon
- [Cave icons](https://www.freepik.com/free-photos-vectors/dark-cave) created by Freepik - flaticon

  
