[![Build Status](https://travis-ci.org/ustits/ColleagueBot.svg?branch=develop)](https://travis-ci.org/ustits/ColleagueBot)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a8b489aca8ac426089f64ba35de99bda)](https://www.codacy.com/app/ustits/ColleagueBot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ustits/ColleagueBot&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/ustits/ColleagueBot/branch/develop/graph/badge.svg)](https://codecov.io/gh/ustits/ColleagueBot)

# Colleague bot for Telegram
## Supported Commands
* `/trigger [trigger] [message]` - shows the message if the trigger was written in the chat
* `/trigger_rm [trigger]` - delete trigger
* `/trigger_ls` - show all triggers available for current chat
* `/stats` - show message statistics for current chat
* `/repeat [cron_patter] [message]` - repeat message by [cron](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html)
* `/help` - lists all available commands

## Version info

### 0.2

* **[new]** [#18](https://github.com/ustits/ColleagueBot/issues/18): friendly wrappers for repeat command that doesn't need to
specify a full cron expression
* **[new]** [#21](https://github.com/ustits/ColleagueBot/issues/21): admin commands for triggers and repeats
* **[refactoring]** [#4](https://github.com/ustits/ColleagueBot/issues/4): repositories refactoring

