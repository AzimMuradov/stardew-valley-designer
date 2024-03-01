# :memo: Changelog

All notable changes to this project will be documented in this file.

The format is _loosely_ based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project _tries_ to adhere to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

I should note that versions before [0.8.0](#rocket-080---2023-07-08) don't have proper changelog.

## :rocket: [Unreleased] (0.12.0) - 2024-XX-XX

## :rocket: [0.11.0] - 2024-03-02

_Supported Stardew Valley version - 1.5.6_

### Desktop & Web

#### Added

- :sparkles: Add player name and farm name text fields
- :sparkles: Support connected textures for floors and fences
- :sparkles: Support seasons

#### Changed

- :sparkles: Include player name and farm name in the design format
- :sparkles: Include palette and options in design format
- :nail_care: Add animation for the object counter widget

#### Fixed

- :wrench: Fix a bug due to which a "shaped eraser" didn't take into account invisible layers
- :wrench: Fix spacing bug in the palette

### Desktop

#### Added

- :sparkles: Add user designs gallery in the main menu

### Web

#### Added

- :sparkles: Support design format
  - Add "open design" button
  - Add "save design as..." button
- :sparkles: Add "new design" button
- :sparkles: Add "import from save" button
- :sparkles: Add "save design as an image" button

## :rocket: [0.10.2] - 2024-02-04

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Fixed

- :wrench: Fix file picker and file saver bugs

## :rocket: [0.10.1] - 2024-02-04

_Supported Stardew Valley version - 1.5.6_

### Desktop & Web

#### Changed

- :nail_care: Rename "plan" to "design"

#### Fixed

- :zap: Fix resources performance

### Desktop

#### Fixed

- :wrench: Fix file picker and file saver defaults

## :rocket: [0.10.0] - 2024-02-02

_Supported Stardew Valley version - 1.5.6_

### Desktop & Web

#### Added

- :sparkles: Add experimental design saves
  - Add design format API

#### Changed

- :sparkles: Change Ctrl+Z and Ctrl+Y behavior to be active while pressing
- :nail_care: Improve editor info at the bottom of the screen

### Desktop

#### Added

- :sparkles: Make the desktop application multi-window
- :sparkles: Add experimental design saves
  - Add "open design" button
  - Add "save design" and "save design as..." buttons

#### Changed

- :sparkles: Refactor "save design as an image" button

#### Removed

- :nail_care: Remove splash screen

### Web

#### Added

- :sparkles: Introduce experimental web editor

## :rocket: [0.9.1] - 2023-09-11

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Added

- :sparkles: Show a notification when image is saved

#### Changed

- :sparkles: Use the native file picker instead of the custom one
- :nail_care: Adjust menus design
- :sparkles: Use the native file saver for "save design as an image" button

#### Fixed

- :wrench: Make file dialogs modal
- :zap: Make "save design as an image" button asynchronous (improves performance)

## :rocket: [0.9.0] - 2023-08-19

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Added

- :sparkles: Add "eye dropper" tool
- :sparkles: Support chest colors

#### Changed

- :sparkles: Update info window

#### Fixed

- :wrench: Fix wallpaper and flooring deserialization
- :wrench: Fix deserialization bug with chest type objects
- :wrench: Refactor and fix menus in the editor screen

## :rocket: [0.8.2] - 2023-08-13

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Changed

- :sparkles: Update configs

## :rocket: [0.8.1] - 2023-07-10

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Fixed

- :wrench: Fix objects order on layout

## :rocket: [0.8.0] - 2023-07-08

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Added

- :sparkles: Add "area of effect" hints for scarecrows, sprinklers, bee houses, and junimo huts
- :sparkles: Add more shapes (ellipse, ellipse outline, diamond, diamond outline, line)
- :sparkles: Add various functional information to the editor
  - Add object counter and option for it
  - Add current coordinates and shape size info and option for them

#### Changed

- :nail_care: Change grid and axis design
- :sparkles: Use "pen" as a default tool
- :nail_care: Hide "shapes" menu when using "hand" tool and update "point" image

#### Fixed

- :wrench: Update entity placement restrictions for standard farm layout
- :wrench: Update save data importer to skip invalid objects
- :nail_care: Align pixels in the editor (removes periodic white lines between grid of objects)
- :zap: Improve performance by using `Sequence` variants for various functions

## [0.7.4] - 2023-06-13

## [0.7.3] - 2023-05-29

## [0.7.2] - 2023-05-29

## [0.7.1] - 2023-05-26

## [0.7.0] - 2023-05-18

- Toggle preview
- Save design as an image

## [0.6.0] - 2023-05-18

- New layouts (shed, standard farm)
- Window resizing

## [0.5.0] - 2023-05-07

- Toolkit icons
- Wallpapers and flooring menus

## [0.4.0] - 2023-05-01

- Main menu
- Expanded API for savedata import

## [0.3.0] - 2023-04-14

- Limited API for savedata import

## [0.2.0] - 2023-03-30

- History manager (undo, redo)
- Working design editor (for big shed only)
- Tools (hand, pen, eraser, select)
- Shapes (point, rect, rect outline)
- Toggleables (show grid, show coords)
- Layers visibility
- Entities (various furniture, equipment, floor, etc)
- Big shed layout

## [0.1.4] - 2023-03-30

## [0.1.3] - 2023-03-28

## [0.1.2] - 2023-03-24

## [0.1.1] - 2023-02-24

## [0.1.0] - 2021-08-27

- Initial engine and setup

## [0.0.0] - 2021-06-17

- Initial state


[unreleased]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.10.2...HEAD
[0.10.2]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.10.1...v0.10.2
[0.10.1]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.10.0...v0.10.1
[0.10.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.9.1...v0.10.0
[0.9.1]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.9.0...v0.9.1
[0.9.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.8.2...v0.9.0
[0.8.2]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.8.1...v0.8.2
[0.8.1]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.8.0...v0.8.1
[0.8.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.7.4...v0.8.0
[0.7.4]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.7.3...v0.7.4
[0.7.3]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.7.2...v0.7.3
[0.7.2]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.7.1...v0.7.2
[0.7.1]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.7.0...v0.7.1
[0.7.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.6.0...v0.7.0
[0.6.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.5.0...v0.6.0
[0.5.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.4.0...v0.5.0
[0.4.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.1.4...v0.2.0
[0.1.4]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.1.3...v0.1.4
[0.1.3]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.1.2...v0.1.3
[0.1.2]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.1.1...v0.1.2
[0.1.1]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.1.0...v0.1.1
[0.1.0]: https://github.com/AzimMuradov/stardew-valley-designer/compare/v0.0.0...v0.1.0
[0.0.0]: https://github.com/AzimMuradov/stardew-valley-designer/releases/tag/v0.0.0
