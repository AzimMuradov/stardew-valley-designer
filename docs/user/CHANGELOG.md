# Changelog

All notable changes to this project will be documented in this file.

The format is _loosely_ based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project _tries_ to adhere to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

I should note that versions before [0.8.0](#080---2023-07-08) don't have proper changelog.

## [Unreleased] (0.11.0) - 2024-XX-XX

_Supported Stardew Valley version - 1.5.6_

### Desktop & Web

#### Added

- Add player name and farm name text fields :tada:

#### Changed

- Include player name and farm name in the design format :sparkles:
- Include palette and options in design format :sparkles:

### Desktop

#### Added

- Add user designs gallery in the main menu :tada:

### Web

#### Added

- Support design format :tada:
  - Add "open design" button
  - Add "save design as..." button
- Add "new design" button :tada:
- Add "open save" button :tada:
- Add "save design as an image" button :tada:

## [0.10.2] - 2024-02-04

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Fixed

- Fix file picker and file saver bugs :wrench:

## [0.10.1] - 2024-02-04

_Supported Stardew Valley version - 1.5.6_

### Desktop & Web

#### Changed

- Rename "plan" to "design" :nail_care:

#### Fixed

- Fix resources performance :zap:

### Desktop

#### Fixed

- Fix file picker and file saver defaults :wrench:

## [0.10.0] - 2024-02-02

_Supported Stardew Valley version - 1.5.6_

### Desktop & Web

#### Added

- Add experimental design saves :tada:
  - Add design format API

#### Changed

- Change Ctrl+Z and Ctrl+Y behavior to be active while pressing :sparkles:
- Improve editor info at the bottom of the screen :nail_care:

### Desktop

#### Added

- Make the desktop application multi-window :tada:
- Add experimental design saves :tada:
  - Add "open design" button
  - Add "save design" and "save design as..." buttons

#### Changed

- Refactor "save design as an image" button :sparkles:

#### Removed

- Remove splash screen :nail_care:

### Web

#### Added

- Introduce experimental web editor :tada:

## [0.9.1] - 2023-09-11

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Added

- Show a notification when image is saved :sparkles:

#### Changed

- Use the native file picker instead of the custom one :sparkles:
- Adjust menus design :nail_care:
- Use the native file saver for "save design as an image" button :sparkles:

#### Fixed

- Make file dialogs modal :wrench:
- Make "save design as an image" button asynchronous (improves performance) :zap:

## [0.9.0] - 2023-08-19

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Added

- Add "eye dropper" tool :tada:
- Support chest colors :tada:

#### Changed

- Update info window :sparkles:

#### Fixed

- Fix wallpaper and flooring deserialization :wrench:
- Fix deserialization bug with chest type objects :wrench:
- Refactor and fix menus in the editor screen :wrench:

## [0.8.2] - 2023-08-13

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Changed

- Update configs :sparkles:

## [0.8.1] - 2023-07-10

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Fixed

- Fix objects order on layout :wrench:

## [0.8.0] - 2023-07-08

_Supported Stardew Valley version - 1.5.6_

### Desktop

#### Added

- Add "area of effect" hints for scarecrows, sprinklers, bee houses, and junimo huts :tada:
- Add more shapes (ellipse, ellipse outline, diamond, diamond outline, line) :tada:
- Add various functional information to the editor :tada:
  - Add object counter and option for it
  - Add current coordinates and shape size info and option for them

#### Changed

- Change grid and axis design :nail_care:
- Use "pen" as a default tool :sparkles:
- Hide "shapes" menu when using "hand" tool and update "point" image :nail_care:

#### Fixed

- Update entity placement restrictions for standard farm layout :wrench:
- Update save data importer to skip invalid objects :wrench:
- Align pixels in the editor (removes periodic white lines between grid of objects) :nail_care:
- Improve performance by using `Sequence` variants for various functions :zap:

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
