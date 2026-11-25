# Automaton

A plain Java command-line simulator for small non-deterministic finite automata (NFA). It reads an automaton definition from a text file and checks whether input strings are accepted. There are no external dependencies.

## Requirements

- Java 25 runtime (`java` on `PATH`).

## Run

From the repository root:

```bash
printf '1\nab\n2\n' | java src/RunAutomatonSimulator.java automaton1.txt src/AutomatonSimulator.java src/Automaton.java src/Configuration.java
```

When no file name is supplied as an argument, the program asks for one interactively.

Menu options:

| Option | Description |
| --- | --- |
| `1` | Simulate an input string |
| `2` | Exit |

## Definition file format

The bundled sample definitions use this layout:

```text
Alfabeto a b
Estados 2
Finais 2
Transitions
1 com 'a' => 1
1 com 'b' => 2
2 com 'a' => 1
2 com 'b' => 2
```

- The first line lists the alphabet symbols.
- The second line gives the number of states. States are numbered from `1`.
- The third line lists the final states.
- The remaining lines describe transitions.

The original Portuguese labels (`Alfabeto`, `Estados`, `Finais`) remain in the sample data files because they are part of the input format. Code comments and user-facing output are in English.

## Notes

- `src/CopyOfAutomatonSimulator.java` and `src/secondWay/` are original experimental files kept for reference.
- `src/Automaton.java` and `src/Configuration.java` were reconstructed as source files so the project no longer depends only on precompiled classes in `classes/`.

## License

MIT — see `LICENSE`.
