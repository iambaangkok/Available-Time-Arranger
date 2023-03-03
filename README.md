# Available-Time-Finder

## Problem
My group of friends which consists of people from diverse faculty/majors, are struggling to arrange a time for our weekly badminton session.

## Solution
I created a simple system for us:
1. Everyone fill in their available time in a spreadsheet
2. I export it to csv
3. I use a scala program to compute which time and day of the week can we do activities together

### Specifications
The table have the following columns

| Active |     Name     |      Day of week      | Free time start | Free time end |
|:------:|:------------:|:---------------------:|:---------------:|:-------------:|
| 1 or 0 | alphaNumeric | M, T, W, Th, F, S, Su |     0-2400      |    0-2400     |


Definition for each column

|     Column      |                                         Definition                                          |
|:---------------:|:-------------------------------------------------------------------------------------------:|
|     Active      |  States whether this row is active or not. So you don't need to delete/rewrite entire row.  |
|      Name       |                               Name / Nick name of the person                                |
|   Day of week   |                       The day at which they have some available time                        |
| Free time start |                           The start of your free time on that day                           |
|  Free time end  |                            The end of your free time on that day                            |


**Notes**
- Days at which there are no data for that person will count as that person being NOT available the entire day
- Example use case for ```Active```: I would likely to be available the same time for every Monday, but this Monday I might be busy, but if I am not busy, I will be available at this time)
- Currently, the program does not support multiple time ranges in the same day
- Currently, the program does not have much error detections

**Planned features**
- Multiple time ranges in the same day
- Error detections
- Manual exclusion of some people (in case they say "This week I'm frickin busy!")
- Auto report?