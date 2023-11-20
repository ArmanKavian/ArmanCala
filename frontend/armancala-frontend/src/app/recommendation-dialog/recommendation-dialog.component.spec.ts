import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendationDialogComponent } from './recommendation-dialog.component';

describe('RecommendationDialogComponent', () => {
  let component: RecommendationDialogComponent;
  let fixture: ComponentFixture<RecommendationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecommendationDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RecommendationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
