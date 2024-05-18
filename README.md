[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=11232751&assignment_repo_type=AssignmentRepo)
# Minecraft Secure API
This is the README file for Assignment 3 in Programming Studio 2 (COSC2804).

Please indicate where to find your video demo. You can include it in this repo if it's small enough, or alternatively use a video sharing platform, such as YouTube. Any widely supported video format is fine (.mp4, .avi, .mkv, etc.)

# Mandatory: Student contributions
Please summarise each team member's contributions here. Include both an approximate, percentage-based breakdown (e.g., "Anna: 25%, Tom: 25%, Claire: 25%, Halil: 25%") **and a high-level summary of what each member worked on, in dot-point form**.

Ideally, any disputes regarding the contributions should be resolved prior to submission, but if there is an unresolved dispute then please indicate that this is the case. Do not override each other's contribution statements immediately prior to the deadline -- this will be viewed dimly by the markers!

### Jacob
#### (0.75 * 15) + (0.5 * 5) = 13.75/35 => 39.2%
- Implementation
- Video editing

### Miguel 
#### (0.125 * 15) + (0.25 * 5) + (0.5 * 15) = 10.625/35 => 30.4%
- Report
- Video script
- Code review

### Steven
#### (0.125 * 15) + (0.25 * 5) + (0.5 * 15) = 10.625/35 => 30.4%
- Report
- Video script
- Code review

### PHASE 1 - REPORT ###
Our report can be found in the root of this git, named 'report.pdf'.

Alternatively:

https://docs.google.com/document/d/1QC6-hrXeQ6aUv4C5SY2KSTm1pUbmmvNYxUw9z3_gKPY/edit?usp=sharing

### PHASE 2 - IMPLEMENTATION ###
- /ELCI/ folder of this git contains the modified Java code for our secured ELCI plugin.
  - pom.xml was modified to add certain dependencies. 
  - Maven should automatically add these, but we have provided a compiled JAR with dependencies (ELCI-1.12.1-SECURE.jar).
- /mcpi/ folder of this git contains the modified Python code for our secured MCPI library.
  - This requires the cryptography package to be installed. 
- generate_keys.py is the code used to generate our two keys, enc.key and mac.key. 
- mcpi_sec.py is the code used to demonstrate the security vulnerability/fix in the video.

### PHASE 3 - VIDEO ###
Our video can be found on Youtube:

https://www.youtube.com/watch?v=QmWBaR6XctM
