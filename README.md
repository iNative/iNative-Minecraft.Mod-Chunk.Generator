# OpenCore Mod — Fabric 1.21.1

Mod ini menambahkan **dimensi OpenCore** ke Minecraft:
- **Terrain**: Overworld persis (biome normal, struktur lengkap)
- **Sky / Fog / Ambience**: The End (langit gelap, efek void, musik The End)

---

## Cara Install

1. Install **Fabric Loader 0.16.5+** untuk Minecraft 1.21.1
2. Install **Fabric API** (`fabric-api-0.102.0+1.21.1.jar`)
3. Drop `opencore-mod-1.0.0.jar` ke folder `mods/`
4. Jalankan game!

---

## Commands

| Command | Fungsi |
|---|---|
| `/teleport_dim` | Teleport ke dimensi OpenCore |
| `/teleport DIM` | Alias command yang sama |

> **Note:** Butuh OP (permission level 2) untuk menggunakan command.

---

## Cara Build dari Source

**Requirement:**
- Java 21
- Gradle 8.8 (sudah include via wrapper)

```bash
# Clone / extract project
cd opencore-mod

# Build
./gradlew build

# Output jar ada di:
build/libs/opencore-mod-1.0.0.jar
```

---

## Struktur Dimensi

| Property | Value |
|---|---|
| Namespace | `opencore:opencore` |
| Dimension Type Effects | `minecraft:the_end` |
| Terrain Generator | `minecraft:noise` (overworld settings) |
| Biome Source | `minecraft:multi_noise` (overworld preset) |
| Natural | `true` |
| Has Skylight | `false` (seperti The End) |
| Ambient Light | `0.0` (gelap seperti The End) |
| Infiniburn | `#minecraft:infiniburn_overworld` |

---

## Teknis: Kenapa `"effects": "minecraft:the_end"`?

Field `effects` di `dimension_type` JSON adalah yang mengontrol:
- **Sky color** → Hitam void The End
- **Fog color** → Ungu gelap The End
- **Mood sound** → Ambience The End
- **Music** → Musik The End

Sedangkan `generator` terpisah dan mengontrol terrain — jadi kita bisa kombinasi keduanya.

---

## License
MIT
