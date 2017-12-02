[![Build Status](https://travis-ci.org/ustits/ColleagueBot.svg?branch=develop)](https://travis-ci.org/ustits/ColleagueBot)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a8b489aca8ac426089f64ba35de99bda)](https://www.codacy.com/app/ustits/ColleagueBot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ustits/ColleagueBot&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/ustits/ColleagueBot/branch/develop/graph/badge.svg)](https://codecov.io/gh/ustits/ColleagueBot)

# Colleague bot for Telegram

## Supported Commands

* `/trigger [trigger] [message]` - shows the message if the trigger was written in the chat
* `/trigger_rm [trigger]` - delete trigger
* `/trigger_ls` - show all triggers available for current chat
* `/ignore` - tell bot to ignore your messages
* `/state_switch [state]` - change trigger response strategy
  * `all` - default mode, that returns every possible trigger
  * `random` - returns one random trigger
  * `nothing` - returns no trigger
  * `first` - returns first found trigger
  * `last` - returns last found trigger
  * `periodic` - returns one random trigger with a specified chance
* `/state` - show current trigger response strategy
* `/repeat [cron_patter] [message]` - repeat message by [cron](http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html)
  * `/repeat_d [hh:mm]` - repeat daily
  * `/repeat_wd [hh:mm]` - repeat on work days
  * `/repeat_we [hh:mm]` - repeat on weekends
* `/stats` - show message statistics for current chat
* `/help` - lists all available commands

## Version info

### 0.3

* **[new]** [#33](https://github.com/ustits/ColleagueBot/issues/33): 
added multiple trigger processing strategies
* **[new]** [#39](https://github.com/ustits/ColleagueBot/issues/39):
admin can limit trigger's message length via config file or command
* **[new]** [#47](https://github.com/ustits/ColleagueBot/issues/47):
users can now switch `ignore` mode that will indicate, that bot must not responde
to their messages
* **[bug]** [#32](https://github.com/ustits/ColleagueBot/issues/32):
checking for quoting when adding new trigger
* **[bug]** [#34](https://github.com/ustits/ColleagueBot/issues/34):
printing list of all triggers in several messages if message length was exceeded
* **[bug]** [#35](https://github.com/ustits/ColleagueBot/issues/35):
fixed the bug with triggers consisted of spaces

### 0.2

* **[new]** [#18](https://github.com/ustits/ColleagueBot/issues/18): 
friendly wrappers for repeat command that doesn't need to specify a full 
cron expression
* **[new]** [#21](https://github.com/ustits/ColleagueBot/issues/21): 
admin commands for triggers and repeats
* **[refactoring]** [#4](https://github.com/ustits/ColleagueBot/issues/4): 
repositories refactoring

