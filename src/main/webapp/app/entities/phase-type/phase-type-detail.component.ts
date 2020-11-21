import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPhaseType } from 'app/shared/model/phase-type.model';

@Component({
  selector: 'jhi-phase-type-detail',
  templateUrl: './phase-type-detail.component.html',
})
export class PhaseTypeDetailComponent implements OnInit {
  phaseType: IPhaseType | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phaseType }) => (this.phaseType = phaseType));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
