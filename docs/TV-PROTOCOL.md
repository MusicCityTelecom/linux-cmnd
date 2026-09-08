# TV protocol (capture evidence v0.1)

Evidence uses private captures; fixtures are described without captured device credentials.

- Discovery: `discovery+config.pcapng`, TCP stream 1085, frames 5523/5576. `POST /WIXP` to TV:9079 with `Svc=WebListeningServices`, `SvcVer=1.0`, `CmdType=Request`, `Fun=TVDiscoveryService`. Response contains power, IP, MAC, model, room ID, serial, VSecure TV ID, and unique ID. Responses observed at service 4.20 and 4.7.
- Power: `idchange+wol.pcapng`, streams 9/23/47. Change `PowerService`, `ToPowerState=On|Standby`; response includes current state, error, transition, and identity. No conventional WOL dissector, UDP/7, or UDP/9 traffic was observed.
- Room ID: stream 65 commands `IPCloneService` to fetch `RoomSpecificSettings.zip`; stream 69 retrieves it. The ZIP contains `RoomSpecificSettings.xml` plus `TVSettings.xml`; captured value was `7200`. Stream 68 reports InProgress then Successful, but no later discovery readback proves application.
- Callbacks: TV JSON status/poll traffic uses `/SmartInstall/webservices.jsp`; root `/webservices.jsp` is multipart logging and is not interchangeable.
- Resume: HTTP 206 confirmed at frames 19439 (`515556-522690/522691`) and 19451 (`15846-22158/22159`).

Observed transport had no HTTP Authorization and reported non-secured transit, while secure-command capability varied by TV. This does not establish universal lack of authentication. Room IDs are strings: both `00704` and `704` were observed for one identity.
