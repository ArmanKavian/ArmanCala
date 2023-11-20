import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-recommendation-dialog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './recommendation-dialog.component.html',
  styleUrl: './recommendation-dialog.component.scss'
})
export class RecommendationDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<RecommendationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  close() {
    this.dialogRef.close();
  }
}
