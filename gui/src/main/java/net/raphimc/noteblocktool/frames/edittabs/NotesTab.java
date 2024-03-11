/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocktool.frames.edittabs;

import net.lenni0451.commons.swing.GBC;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.util.SongUtil;
import net.raphimc.noteblocktool.elements.IntFormatterFactory;
import net.raphimc.noteblocktool.frames.ListFrame;
import net.raphimc.noteblocktool.util.MinecraftOctaveClamp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotesTab extends EditTab {

    private JComboBox<MinecraftOctaveClamp> octaveClamp;
    private JSpinner volumeSpinner;

    public NotesTab(final List<ListFrame.LoadedSong> songs) {
        super("Notes", songs);
    }

    @Override
    protected void initComponents(JPanel center) {
        JPanel octaveClamp = new JPanel();
        octaveClamp.setLayout(new GridBagLayout());
        octaveClamp.setBorder(BorderFactory.createTitledBorder("Minecraft Octave Clamp"));
        center.add(octaveClamp);
        GBC.create(octaveClamp).grid(0, 0).insets(5, 5, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JComboBox<>(MinecraftOctaveClamp.values()), comboBox -> {
            this.octaveClamp = comboBox;
        });
        GBC.create(octaveClamp).grid(0, 1).insets(5, 5, 5, 5).width(2).weightx(1).fill(GBC.HORIZONTAL).add(html(
                "<b>NONE:</b> Don't change the key of the note.",
                "<b>INSTRUMENT_SHIFT:</b> \"Transposes\" the key of the note by shifting the instrument to a higher or lower sounding one. This often sounds the best of the three methods as it keeps the musical key the same and only changes the instrument.",
                "<b>TRANSPOSE:</b> Transposes the key of the note to fall within minecraft octave range. Any key below 33 will be transposed up an octave, and any key above 57 will be transposed down an octave.",
                "<b>CLAMP:</b> Clamps the key of the note to fall within minecraft octave range. Any key below 33 will be set to 33, and any key above 57 will be set to 57."
        ));

        JPanel volume = new JPanel();
        volume.setLayout(new GridBagLayout());
        volume.setBorder(BorderFactory.createTitledBorder("Volume"));
        center.add(volume);
        GBC.create(volume).grid(0, 0).insets(5, 5, 5, 5).anchor(GBC.LINE_START).add(html("Remove notes quieter than:"));
        GBC.create(volume).grid(1, 0).insets(5, 5, 5, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)), spinner -> {
            this.volumeSpinner = spinner;
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFormatterFactory(new IntFormatterFactory("%"));
        });
    }

    @Override
    public void apply(final Song<?, ?, ?> song, final SongView<?> view) {
        SongUtil.applyToAllNotes(view, note -> ((MinecraftOctaveClamp) this.octaveClamp.getSelectedItem()).correctNote(note));
        SongUtil.removeSilentNotes(view, (int) this.volumeSpinner.getValue());
    }

}
