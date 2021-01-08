import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBatch } from 'app/shared/model/batch.model';

@Component({
  selector: 'jhi-batch-detail',
  templateUrl: './batch-detail.component.html',
})
export class BatchDetailComponent implements OnInit {
  batch: IBatch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batch }) => (this.batch = batch));
  }

  previousState(): void {
    window.history.back();
  }
}
