/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib.format.midi;

import net.raphimc.noteblocklib.format.midi.data.MidiData;
import net.raphimc.noteblocklib.format.midi.header.MidiHeader;

import javax.sound.midi.InvalidMidiDataException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class MidiParser {

    public static MidiSong parse(final byte[] bytes, final File sourceFile) throws InvalidMidiDataException, IOException {
        final MidiHeader header = new MidiHeader(new ByteArrayInputStream(bytes));
        final MidiData data = new MidiData(header, new ByteArrayInputStream(bytes));

        return new MidiSong(sourceFile, header, data);
    }

}
