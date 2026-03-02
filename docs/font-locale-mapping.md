# Font × Locale Mapping (Frjar Customer App)

User-defined mapping for applying fonts by app locale.

| Locale   | Font(s)     | res/font files |
|----------|-------------|-----------------|
| **Arabic**  | Alexandria | `alexandria_*.ttf` (bold, extrabold, extralight, light, regular, semibold) |
| **Hindi**   | Hind       | `hind_*.ttf` (bold, light, regular, semibold) |
| **English** | Poppins + Inter | `poppins_*.ttf`, `inter_*.ttf` |
| **Urdu**    | Nafees     | `nafees.ttf` |

## Locale qualifiers in project
- `values/` — default (English)
- `values-ar/` — Arabic
- `values-hi/` — Hindi
- `values-ur/` — Urdu

## Implementation note
Use this mapping when implementing locale-based typography (e.g. in `Type.kt` / theme or font-family XMLs).  
English can use Poppins for headings and Inter for body, or a single choice per text style.
