# 🏦 Penthouses - Minecraft 1.12.2 Custom Plugin

[![Minecraft](https://img.shields.io/badge/Minecraft-62B47A?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spigot](https://img.shields.io/badge/Spigot-FFAA00?style=for-the-badge&logo=spigot&logoColor=white)](https://www.spigotmc.org/)

> **🌟 Legacy Project from June 2022** - A custom Minecraft plugin that adds unique penthouse functionality to your server

## 🎥 Showcase
[![Watch the video](https://img.youtube.com/vi/1eaD9zgPOKk/maxresdefault.jpg)](https://youtu.be/1eaD9zgPOKk)

<div align="center">
  <p><i>👆 Watch the showcase video above or <a href="https://youtu.be/1eaD9zgPOKk">click here to watch the video on YouTube</a></i></p>
</div>

## ✨ Features

### 🏢 Penthouse System
- **🔑 Custom Building System** - Create and manage your own penthouse
- **🔒 Security Features** - Protect your penthouse from unwanted visitors
- **👥 Management System** - Add and remove users to manage access to your penthouse
- **💰 Economy Integration** - Buy and sell penthouses with configurable refund rates
- **🏗️ Building Management** - Create multiple buildings with unique NPCs and PCs

### 🛠️ Technical Features
- **⚡ Event-Driven Architecture** - Efficient event handling system
- **🔧 Server Integration** - Seamless integration with Minecraft servers
- **📊 Data Management** - Persistent storage for penthouse data
- **🌐 Multi-language Support** - Built-in Dutch language support with easy translation system
- **🎮 Interactive NPCs** - Custom NPCs for building entrances with configurable skins

## 🏗️ Architecture

Penthouses follows **clean architecture principles** with a well-structured Java codebase:

```
src/
├── commands/        # Command implementations
├── listeners/       # Minecraft event handlers
├── managers/        # Business logic layer
└── /                # Helper functions and utilities
```

### 🔧 Key Technologies

- **Java** - Core programming language
- **Spigot API** - Minecraft server API
- **Event-Driven Design** - Scalable architecture for handling Minecraft events
- **Dependency Injection** - Clean separation of concerns

## ⚙️ Configuration

### General Settings
```yaml
# Money back percentage when selling a penthouse
MoneyBackPercentage: 80

# Use block for menu instead of npc
Use-Block: false
Block: 'REDSTONE_BLOCK'
```

### NPC Settings
```yaml
# NPC display name format
NPC-Name: "&8< &c%building% &8>"

# Default NPC skin
NPC-Skin: "Mister_Miel"
```

## 🚀 Getting Started

### Prerequisites
- Java 8
- Minecraft Server 1.12.2
- Spigot/Paper Server

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/MielVelden/Penthouses.git
   cd Penthouses
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Install the plugin**
   - Copy the generated .jar file to your server's plugins folder
   - Restart your server

## 🎮 Usage

### Setting Up Penthouses

1. Use `/penthouse create` in-game
2. Select your desired location
3. Start building your penthouse!

### Available Commands

#### Player Commands
- `/penthouse addmember (player)` - Add a player to your penthouse
- `/penthouse removemember (player)` - Remove a player from your penthouse  
- `/penthouse verlaat` - Leave the current building
- `/penthouse verkoop` - Sell your penthouse

#### Admin Commands
- `/padmin createbuilding (name)` - Create a new building
- `/padmin setbuildingspawn (building)` - Set the spawn location of a building
- `/padmin deletebuilding (building)` - Delete a building
- `/padmin create (building) (price)` - Create a new room
- `/padmin delete` - Delete the current room
- `/padmin setspawn` - Set the spawn location
- `/padmin spawnnpc (building)` - Spawn an NPC for a building
- `/padmin setpc (building)` - Create a building PC

## 🏆 Key Highlights

- **🔥 Legacy Project** - Created in June 2022
- **📈 Clean Architecture** - Well-structured codebase
- **🛡️ Type Safety** - Full Java implementation
- **⚡ Performance Optimized** - Efficient event handling
- **🔧 Maintainable** - Well-documented code
- **🌍 Dutch Language** - Built-in Dutch language support
- **💰 Economy System** - Integrated economy features

## 📝 Note

This project is a legacy project from June 2022 and is no longer actively maintained. It is specifically designed for Minecraft version 1.12.2 and may not be compatible with newer versions. This repository is maintained for historical purposes and to showcase past development work.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

- **GitHub**: [MielVelden](https://github.com/MielVelden)