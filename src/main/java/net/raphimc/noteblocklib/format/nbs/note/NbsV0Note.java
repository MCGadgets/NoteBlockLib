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
package net.raphimc.noteblocklib.format.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class NbsV0Note extends NbsNote {

    @SuppressWarnings("UnstableApiUsage")
    public NbsV0Note(final LittleEndianDataInputStream dis) throws IOException {
        super(dis.readByte(), dis.readByte());
    }

    public NbsV0Note(final byte instrument, final byte key) {
        super(instrument, key);
    }

}
