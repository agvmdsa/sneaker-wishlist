import { describe, expect, it } from 'vitest';
import { getValidTransitions } from './status-transitions';

describe('getValidTransitions', () => {
  it('allows WANT to transition only to OWNED', () => {
    expect(getValidTransitions('WANT')).toEqual(['OWNED']);
  });

  it('allows OWNED to transition to SOLD or DONATED', () => {
    expect(getValidTransitions('OWNED')).toEqual(['SOLD', 'DONATED']);
  });

  it('treats SOLD as a terminal status', () => {
    expect(getValidTransitions('SOLD')).toEqual([]);
  });

  it('treats DONATED as a terminal status', () => {
    expect(getValidTransitions('DONATED')).toEqual([]);
  });
});
