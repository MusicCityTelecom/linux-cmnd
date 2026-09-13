# Initial source-reconstruction repairs

All modifications are confined to editable modules. `source-map.json` anchors
each file to the untouched recovered baseline. No vendor feature was removed.
Compiler success alone does not establish behavioral equivalence.

| Repair | Reason/evidence | Remaining validation |
| --- | --- | --- |
| Boot loader `LaunchedURLClassLoader` in CAS, User Management and SmartControl | Explicit `PrivilegedExceptionAction<Void>` selects the exception-capable overload represented by the recovered catch structure | Original/rebuilt bytecode/API and isolated launcher tests |
| Boot loader `JarFile.iterator()` | Covariant iterator element type needs an explicit erased bridge cast in recovered Java | Iterator contracts and jar loading |
| Boot loader `MainMethodRunner` | Pass `String[]` as one reflection argument; CFR independently reconstructs `new Object[]{this.args}` | Offline argument forwarding regression |
| Java compilation target 17 | Supported project runtime; `CharSequence.isEmpty()` override is valid at this target | Match deployments to JDK/JRE 17; no older runtime claim |
| CAS `lombok.Generated` compile-only declaration | Missing build metadata annotation; annotation processing disabled; declaration excluded from output JAR | Verify class-retained annotation metadata against original; this is not a replacement business-logic class |
| SmartControl enum utilities | Restore erased generic casts and typed enum-array iteration | Enum/value contracts and original API comparison |
| Two HTNG clients use CFR baseline | Vineflower failed to reconstruct one method and confused reused locals in the other | PMS/HTNG simulation; no network calls during compilation |
| HTNG extension lists use `org.w3c.dom.Element` | Match `TPAExtensionsType.getAny()` types instead of decompiler-inferred `Object` | Generated XML and guest/room event contracts |
| SmartInstall `PmsUtils` | Restore `exceptionCount = 1`, independently present in CFR, in place of nonsensical `int var11 = true` | Offline empty-device/error-path comparison |
| TigerTmsServlet DOM loop | Preserve the element cast while iterating the declared DOM node list | Bill parsing fixtures |
| PSGCatalogGenerator environment map | Restore typed string keys/values; no RF logic or return behavior removed | Environment/path review remains pending |
| WelcomeLogoUtils switch scope | Restore separate lexical scopes for case-local variables | Welcome image/app upload fixtures |
| Container dependency precedence | Tomcat API before stale WAR-bundled servlet API, preserving all input libraries | Isolated web container/linkage testing |
