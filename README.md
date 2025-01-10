<a href="https://developer.oracle.com">![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=black)</a>
<a href="https://java.com">![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)</a>

# Rockwell Spectrum CTI System

Rockwell Spectrum Enterprise Telephony Server and Client System

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Logging](#logging)
- [Database Schema](#database-schema)
- [Build and Deployment](#build-and-deployment)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Rockwell Spectrum Enterprise Telephony Server is a comprehensive solution designed to manage telephony communications within an enterprise environment. This server handles client connections, manages call information, and integrates with various telephony hardware and software components.

## Features

- **Client Management**: Handles multiple client connections and manages their states.
- **Call Information Handling**: Parses and processes call information such as DNIS, ANI, and call status.
- **Logging**: Comprehensive logging of server activities and client interactions.
- **Database Integration**: Stores and retrieves call and client information from a database.
- **Configuration Management**: Easily configurable through properties files and database settings.

## Installation

To install the Rockwell Spectrum Enterprise Telephony Server, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/museum-rockwell-spectrum-cti.git
    cd museum-rockwell-spectrum-cti
    ```

2. Set up the database:
    - Create the database schema using the SQL scripts provided in the `Database Schema` directory.

3. Configure the server:
    - Update the configuration files in the [Client](http://_vscodecontentref_/0) and [Server](http://_vscodecontentref_/1) directories as needed.

## Usage

To start the server, navigate to the [Server](http://_vscodecontentref_/2) directory and run the build script:

```sh
./build.sh
```

This will compile the server and start it, listening for client connections.

## Configuration

Configuration files are located in the Client and Server directories. Key configuration files include:

JServer35.deployment.properties: Contains deployment-specific properties.
JServer35.jpr: Project file for JBuilder.
build.xml: Ant build script for compiling and deploying the server.

## Logging

Logging is handled by the LogFile class, which writes log entries to a file. Log files are created in the Sample Log Files directory with a timestamp in the filename.

## Database Schema

The database schema is defined in the Database Schema directory. Key tables include:

Spectrum: Stores information about the telephony spectrum.
ClientList: Stores information about connected clients.
DNIS_List: Stores DNIS (Dialed Number Identification Service) information.
Build and Deployment
To build and deploy the server, use the Ant build script located in the Server directory:

This will clean the previous build, compile the source code, and deploy the server.

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests for any enhancements or bug fixes.


## Contact
<a href="mailto:Ronald.Redmer@gmail.com">![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)</a>
<a href="https://signal.link/call/#key=kmxm-qmqs-zcxx-znxm-tbpm-fgpf-xxzt-gsdh">![Signal](https://img.shields.io/badge/Signal-3A76F0?style=for-the-badge&logo=signal&logoColor=white)</a>
<a href="https://t.me/RonaldRedmer">![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)</a>
<a href="https://wa.me/12484972761">![WhatsApp](https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=WhatsApp&logoColor=white)</a>

### Follow
<a href="https://bsky.app/profile/rredmer.bsky.social">![BlueSky](https://img.shields.io/badge/Bluesky-0285FF?logo=bluesky&logoColor=fff&style=for-the-badge)</a>
<a href="https://devrant.com/users/rredmer">![DevRant](https://img.shields.io/badge/devRant-F99A66?style=for-the-badge&logo=devrant&logoColor=white)</a>
<a href="https://discordapp.com/users/RedZone">![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)</a>
<a href="https://matrix.to/#/@ronaldredmer:matrix.org">![Element](https://img.shields.io/badge/Element-0DBD8B?style=for-the-badge&logo=element&logoColor=white)</a>
<a href="https://www.kaggle.com/ronredmer">![Kaggle](https://img.shields.io/badge/Kaggle-20BEFF?style=for-the-badge&logo=Kaggle&logoColor=white)</a>
<a href="https://www.linkedin.com/in/rredmer/">![LinkIn Profile](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)</a>
<a href="https://mastodon.social/@RonaldRedmer">![Mastodon](https://img.shields.io/badge/Mastodon-6364FF?style=for-the-badge&logo=Mastodon&logoColor=white)</a>
<a href="https://www.quora.com/profile/Ron-Redmer">![Quora Profile](https://img.shields.io/badge/Quora-%23B92B27.svg?&style=for-the-badge&logo=Quora&logoColor=white)</a>
<a href="https://www.reddit.com/user/RonaldRedmer/">![Reddit](https://img.shields.io/badge/Reddit-FF4500?style=for-the-badge&logo=reddit&logoColor=white)</a>
<a href="https://stackoverflow.com/users/29130217/ron-redmer">![Stack Overflow](https://img.shields.io/badge/Stack_Overflow-FE7A16?style=for-the-badge&logo=stack-overflow&logoColor=white)</a>
<a href="https://www.tumblr.com/blog/ronaldredmer">![Tumblr](https://img.shields.io/badge/Tumblr-%2336465D.svg?&style=for-the-badge&logo=Tumblr&logoColor=white)</a>
<a href="https://www.twitch.tv/ronaldredmer">![Twitch](https://img.shields.io/badge/Twitch-9146FF?style=for-the-badge&logo=twitch&logoColor=white)</a>
<a href="https://www.youtube.com/@RonaldRedmer">![YouTube](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)</a>

<a href="https://github.com/rredmer">![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)</a>
<a href="https://dev.to/rredmer">![Dev.to](https://img.shields.io/badge/dev.to-0A0A0A?style=for-the-badge&logo=devdotto&logoColor=white)</a>
<a href="https://medium.com/@ronald.redmer">![Medium](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)</a>
<a href="https://x.com/ron_redmer">![X (formerly Twitter) Follow](https://img.shields.io/twitter/follow/ron_redmer)</a>

### Sponsor
<a href="https://github.com/sponsors/rredmer">![Github Sponsor](https://img.shields.io/badge/sponsor-30363D?style=for-the-badge&logo=GitHub-Sponsors&logoColor=#white)</a>
<a href="https://buymeacoffee.com/rredmer">![Buy me a cofee](https://img.shields.io/badge/Buy_Me_A_Coffee-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)</a>
<a href="https://patreon.com/TechnologyPlayground">![Patreon](https://img.shields.io/badge/Patreon-F96854?style=for-the-badge&logo=patreon&logoColor=white)</a>
<a href="https://paypal.me/RonaldRedmer">![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)</a>
<a href="https://donate.stripe.com/9AQg0G6on55a1pK288">![Stripe](https://img.shields.io/badge/Stripe-626CD9?style=for-the-badge&logo=Stripe&logoColor=white)</a>

### Web
<a href="https://ronaldredmer.com">![Ronald Redmer Portfolio](https://img.shields.io/badge/Ronald%20Redmer%20Portfolio-blue?style=for-the-badge)</a>
<a href="https://a1si.com">![A1 Systems Integrators LLC](https://img.shields.io/badge/A1%20Systems%20Integrators-blue?style=for-the-badge)</a>
<a href="https://techproductsgroup.com">![Technical Products Group](https://img.shields.io/badge/Technical%20Products%20Group-blue?style=for-the-badge)</a>

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
