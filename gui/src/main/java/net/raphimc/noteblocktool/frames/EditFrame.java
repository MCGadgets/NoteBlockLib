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
package net.raphimc.noteblocktool.frames;

import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocktool.frames.edittabs.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class EditFrame extends JFrame {

    private final List<ListFrame.LoadedSong> songs;
    private final Consumer<ListFrame.LoadedSong> songRefreshConsumer;
    private NotesTab notesTab;
    private ResamplingTab resamplingTab;
    private InstrumentsTab instrumentsTab;
    private MetadataTab metadataTab;

    public EditFrame(final List<ListFrame.LoadedSong> songs, final Consumer<ListFrame.LoadedSong> songRefreshConsumer) {
        this.songs = songs;
        this.songRefreshConsumer = songRefreshConsumer;

        this.setTitle("NoteBlockTool Editor");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);

        this.initComponents();

        this.setMinimumSize(this.getSize());
        this.setVisible(true);
    }

    private void initComponents() {
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        this.setContentPane(root);

        { //Center Panel
            JTabbedPane tabs = new JTabbedPane();
            root.add(tabs, BorderLayout.CENTER);

            tabs.addTab("Notes", this.notesTab = new NotesTab(this.songs, this.songRefreshConsumer));
            tabs.addTab("Resampling", this.resamplingTab = new ResamplingTab(this.songs, this.songRefreshConsumer));
            tabs.addTab("Instruments", this.instrumentsTab = new InstrumentsTab(this.songs, this.songRefreshConsumer));
            tabs.addTab("Metadata", this.metadataTab = new MetadataTab(this.songs, this.songRefreshConsumer));
            if (this.songs.size() != 1) {
                tabs.setEnabledAt(3, false);
                tabs.setToolTipTextAt(3, "This tab is only available when editing a single song");
            }
            for (int i = 0; i < tabs.getTabCount(); i++) {
                EditTab tab = (EditTab) tabs.getComponentAt(i);
                tab.init();
            }
        }
        { //South Panel
            JPanel south = new JPanel();
            south.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.add(south, BorderLayout.SOUTH);

            JButton apply = new JButton("Save");
            apply.addActionListener(e -> {
                for (ListFrame.LoadedSong song : this.songs) {
                    this.instrumentsTab.apply(song.getSong(), song.getSong().getView());
                    this.notesTab.apply(song.getSong(), song.getSong().getView());
                    this.resamplingTab.apply(song.getSong(), song.getSong().getView());
                    this.metadataTab.apply(song.getSong(), song.getSong().getView());
                }
                JOptionPane.showMessageDialog(this, "Saved all changes", "Saved", JOptionPane.INFORMATION_MESSAGE);
                for (ListFrame.LoadedSong song : this.songs) this.songRefreshConsumer.accept(song);
            });
            south.add(apply);
            JButton preview = new JButton("Preview");
            preview.addActionListener(e -> {
                ListFrame.LoadedSong song = this.songs.get(0);
                SongView<?> view = song.getSong().getView().clone();
                this.instrumentsTab.apply(song.getSong(), view);
                this.notesTab.apply(song.getSong(), view);
                this.resamplingTab.apply(song.getSong(), view);
                this.metadataTab.apply(song.getSong(), view);
                SongPlayerFrame.open(song, view);
            });
            if (this.songs.size() != 1) {
                preview.setEnabled(false);
                preview.setToolTipText("Preview is only available for one song at a time");
            }
            south.add(preview);
        }
    }

}
